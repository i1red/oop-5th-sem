import { Table, TableBuilder, createRandomlyFilledTable, ShotResult } from './table/index.js';
import { ComputerPlayer } from './computerplayer.js';
import { CellType } from './table/common.js';

function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export class Game {
    constructor() {
        this._userTable = null;
        this._userTableBuilder = new TableBuilder();
        this._computerTable = createRandomlyFilledTable();
        this._computerPlayer = null;

        this._createUiTables();
        this._addListenersToUserTableCells();


        document.getElementById('userShips').classList.remove('disabled');
        document.getElementById('computerShips').classList.add('disabled', 'gray');
    }

    _createUiTables() {
        for (let table of document.getElementsByClassName('table')) {
            table.innerHTML = '';
            for (let i = 0; i < 100; ++i) {
                table.appendChild(document.createElement('div'));
            }
        }
    }

    _addListenersToUserTableCells() {
        const userShipsTable = document.getElementById('userShips');

        for (let [i, child] of userShipsTable.childNodes.entries()) {
            child.addEventListener('click', () => {
                const m = Math.floor(i / 10);
                const n = i % 10;

                const cellType = this._userTableBuilder.getCellType(m, n);
                if (cellType === CellType.FREE && this._userTableBuilder.checkAndCreateDeck(m, n)) {
                    child.classList.add('deck');
                }
                else if (cellType === CellType.DECK && this._userTableBuilder.checkAndRemoveDeck(m, n)) {
                    child.classList.remove('deck');
                }
                else {
                    alert('Invalid choice!');
                }

                if (this._userTableBuilder.isFilled()) {
                    this._userTable = this._userTableBuilder.createTable();
                    this._computerPlayer = new ComputerPlayer(this._userTable);

                    this._addListenersToComputerTableCells();

                    document.getElementById('computerShips').classList.remove('disabled', 'gray');
                    userShipsTable.classList.add('disabled');
                }
            })
        }
    }

    _addListenersToComputerTableCells() {
        const computerShipsTable = document.getElementById('computerShips');

        for (let [i, child] of computerShipsTable.childNodes.entries()) {
            child.addEventListener('click', async () => {
                let m = Math.floor(i / 10);
                let n = i % 10;

                let userShotData = this._computerTable.shoot(m, n);
                this._updateUiTable(computerShipsTable, child, userShotData);

                if (userShotData.shotResult === ShotResult.MISSED) {
                    computerShipsTable.classList.add('disabled');
                    const userShipsTable = document.getElementById('userShips');

                    while (true) {
                        await sleep(1200);

                        let computerShotData = this._computerPlayer.shoot();
                        let child = userShipsTable.childNodes[computerShotData.cellCoordinates[0] * 10 + computerShotData.cellCoordinates[1]];
                        this._updateUiTable(userShipsTable, child, computerShotData);

                        if (computerShotData.shotResult === ShotResult.MISSED) {
                            break;
                        }

                        if (this._userTable.isDestroyed()) {
                            for (const [m, n] of this._computerTable.getUndamagedDecks()) {
                                computerShipsTable.childNodes[m * 10 + n].classList.add('deck');
                            }
                            return;
                        }
                    }

                    computerShipsTable.classList.remove('disabled');
                }

            })
        }
    }

    _updateUiTable(table, child, shotData) {
        if (shotData.shotResult !== ShotResult.MISSED) {
            child.classList.add('damagedDeck');
            if (shotData.shotResult === ShotResult.DESTROYED) {
                for (const [m, n] of shotData.waterCells) {
                    table.childNodes[m * 10 + n].classList.add('water');
                }
            }
            else {
                const [m, n] = shotData.cellCoordinates;
                const waterCells = [[m - 1, n - 1], [m - 1, n + 1], [m + 1, n - 1], [m + 1, n + 1]];
                for (const [p, q] of waterCells) {
                    if (p >= 0 && p < 10 && q >= 0 && q < 10) {
                        table.childNodes[p * 10 + q].classList.add('water');
                    }
                }
            }
        }
        else {
            child.classList.add('water');
        }
    }
}