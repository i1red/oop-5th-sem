import { CellType, Direction } from './common.js';
import { createTableArray } from './utils.js'
import { Table } from './table.js';


export class StrictTableBuilder {
    constructor() {
        this._tableArray = createTableArray();
        this._shipsLeft = [Number.POSITIVE_INFINITY, 4, 3, 2, 1];
    }

    isFilled() {
        for (let i = 1; i <= 4; ++i) {
            if (this._shipsLeft[i] !== 0) {
                return false;
            }
        }

        return true;
    }

    createTable() {
        return this.isFilled() ? new Table(Array.from(this._tableArray, row => Array.from(row))) : null;
    }

    checkAndCreateShip(m, n, shipSize, direction) {
        if (this._shipsLeft[shipSize] === 0) {
            return false;
        }

        let startM, endM, startN, endN;
        if (direction === Direction.UP || direction === Direction.DOWN) {
            [startN, endN] = [n - 1, n + 1];
            [startM, endM] = direction === Direction.UP ? [m - shipSize, m + 1] : [m - 1, m + shipSize];
        }
        else {
            [startN, endN] = direction === Direction.LEFT ? [n - shipSize, n + 1] : [n - 1, n + shipSize];
            [startM, endM] = [m - 1, m + 1];
        }

        for (let i = startM; i <= endM; ++i) {
            for (let j = startN; j <= endN; ++j) {
                if (i >= 0 && i < 10 && j >= 0 && j < 10 && this._tableArray[i][j] !== CellType.FREE) {
                    return false;
                }
            }
        }

        if (direction === Direction.UP || direction === Direction.DOWN) {
            const [shipStartM, shipEndM] = [startM + 1, endM - 1];
            if (shipStartM >= 0 && shipEndM < 10) {
                for (let i = shipStartM; i <= shipEndM; ++i) {
                    this._tableArray[i][n] = CellType.DECK;
                }
            }
            else {
                return false;
            }
        }
        else {
            const [shipStartN, shipEndN] = [startN + 1, endN - 1];
            if (shipStartN >= 0 && shipEndN < 10) {
                for (let i = shipStartN; i <= shipEndN; ++i) {
                    this._tableArray[m][i] = CellType.DECK;
                }
            }
            else {
                return false;
            }
        }
        --this._shipsLeft[shipSize];

        return true;
    }
}
