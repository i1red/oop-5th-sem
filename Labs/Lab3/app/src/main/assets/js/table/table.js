import { CellType, Direction, ShotResult, ShotData } from './common.js';
import { countConsequtiveCells } from './utils.js';


export class Table {
    constructor(tableArray) {
        this._tableArray = tableArray;
        this._shipsLeft = [0, 4, 3, 2, 1];
    }

    isDestroyed() {
        return this._shipsLeft.every(shipsCount => shipsCount === 0);
    }

    getUndamagedDecks() {
        let undamagedDecks = [];

        console.log(this._tableArray);
        for (let i = 0; i < 10; ++i) {
            for (let j = 0; j < 10; ++j) {
                if (this._tableArray[i][j] === CellType.DECK) {
                    undamagedDecks.push([i, j]);
                }
            }
        }

        return undamagedDecks;
    }

    shoot(m, n) {
        if (this._tableArray[m][n] === CellType.DECK) {
            this._tableArray[m][n] = CellType.DAMAGED_DECK;

            let sizeAndCells = this._getSurroundingCellsAndShipSizeIfDestroyed(m, n);
            if (sizeAndCells.size !== null) {
                --this._shipsLeft[sizeAndCells.size];
                return new ShotData([m, n], ShotResult.DESTROYED, sizeAndCells.cells);
            }

            return new ShotData([m, n], ShotResult.DAMAGED, null);
        }

        return new ShotData([m, n], ShotResult.MISSED, null);
    }

    _getSurroundingCellsAndShipSizeIfDestroyed(m, n) {
        const isDeck = cell => cell === CellType.DECK || cell === CellType.DAMAGED_DECK;
        const isDamagedDeck = cell => cell === CellType.DAMAGED_DECK;

        let topDeckCount = countConsequtiveCells(this._tableArray, m - 1, n, isDeck, Direction.UP);
        let topDamagedDeckCount = countConsequtiveCells(this._tableArray, m - 1, n, isDamagedDeck, Direction.UP);

        let bottomDeckCount = countConsequtiveCells(this._tableArray, m + 1, n, isDeck, Direction.DOWN);
        let bottomDamagedDeckCount = countConsequtiveCells(this._tableArray, m + 1, n, isDamagedDeck, Direction.DOWN);

        let leftDeckCount = countConsequtiveCells(this._tableArray, m, n - 1, isDeck, Direction.LEFT);
        let leftDamagedDeckCount = countConsequtiveCells(this._tableArray, m, n - 1, isDamagedDeck, Direction.LEFT);

        let rightDeckCount = countConsequtiveCells(this._tableArray, m, n + 1, isDeck, Direction.RIGHT);
        let rightDamagedDeckCount = countConsequtiveCells(this._tableArray, m, n + 1, isDamagedDeck, Direction.RIGHT);

        let size = null;
        let surroundingCells = [];
        const pushCellIfExists = (p, q) => {
            if (p >= 0 && p < 10 && q >= 0 && q < 10) {
                surroundingCells.push([p, q])
            }
        }

        if (topDeckCount > 0 || bottomDeckCount > 0) {
            if (topDeckCount === topDamagedDeckCount && bottomDeckCount === bottomDamagedDeckCount) {
                let cellsStartM = m - topDamagedDeckCount - 1;
                let cellsEndM = m + bottomDamagedDeckCount + 1;

                pushCellIfExists(cellsStartM, n);
                pushCellIfExists(cellsEndM, n);
                for (let i = cellsStartM; i <= cellsEndM; ++i) {
                    pushCellIfExists(i, n - 1);
                    pushCellIfExists(i, n + 1);
                }
                size = topDamagedDeckCount + bottomDamagedDeckCount + 1;
            }
        }
        else {
            if (leftDeckCount === leftDamagedDeckCount && rightDeckCount === rightDamagedDeckCount) {
                let cellsStartN = n - leftDamagedDeckCount - 1;
                let cellsEndN = n + rightDamagedDeckCount + 1;

                pushCellIfExists(m, cellsStartN);
                pushCellIfExists(m, cellsEndN);
                for (let i = cellsStartN; i <= cellsEndN; ++i) {
                    pushCellIfExists(m - 1, i);
                    pushCellIfExists(m + 1, i);
                }

                size = leftDamagedDeckCount + rightDamagedDeckCount + 1;
            }
        }

        return {
            size: size,
            cells: surroundingCells,
        };
    }
}
