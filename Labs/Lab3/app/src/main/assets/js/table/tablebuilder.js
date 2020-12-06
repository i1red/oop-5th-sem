import { CellType, Direction } from './common.js';
import { createTableArray, countConsequtiveCells, checkDiagonal } from './utils.js'
import { Table } from './table.js';


export class TableBuilder {
    constructor() {
        this._tableArray = createTableArray();
        this._shipsLeft = [Number.POSITIVE_INFINITY, 4, 3, 2, 1];
    }

    getCellType(m, n) {
        return this._tableArray[m][n];
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

    checkAndCreateDeck(m, n) {
        if (!checkDiagonal(this._tableArray, m, n)) {
            return false;
        }

        const [firstNeighbourSize, secondNeighbourSize] = this._getNeighbourSizes(m, n);
        const newShipSize = firstNeighbourSize + secondNeighbourSize + 1;

        if (newShipSize > 4) {
            return false;
        }

        let updatedShipsLeft = Array.from(this._shipsLeft);

        --updatedShipsLeft[newShipSize];
        ++updatedShipsLeft[firstNeighbourSize];
        ++updatedShipsLeft[secondNeighbourSize];

        if (!this._checkUpdatedShipsLeft(updatedShipsLeft)) {
            return false;
        }

        this._shipsLeft = updatedShipsLeft;

        this._tableArray[m][n] = CellType.DECK;

        return true;
    }

    checkAndRemoveDeck(m, n) {
        const [firstNeighbourSize, secondNeighbourSize] = this._getNeighbourSizes(m, n);
        const shipSize = firstNeighbourSize + secondNeighbourSize + 1;

        let updatedShipsLeft = Array.from(this._shipsLeft);

        ++updatedShipsLeft[shipSize];
        --updatedShipsLeft[firstNeighbourSize];
        --updatedShipsLeft[secondNeighbourSize];

        if (!this._checkUpdatedShipsLeft(updatedShipsLeft)) {
            return false;
        }

        this._shipsLeft = updatedShipsLeft;

        this._tableArray[m][n] = CellType.FREE;

        return true;
    }

    _getNeighbourSizes(m, n) {
        const isDeck = cell => cell === CellType.DECK;

        let topShipSize = countConsequtiveCells(this._tableArray, m - 1, n, isDeck, Direction.UP);
        let bottomShipSize = countConsequtiveCells(this._tableArray, m + 1, n, isDeck, Direction.DOWN);
        let leftShipSize = countConsequtiveCells(this._tableArray, m, n - 1, isDeck, Direction.LEFT);
        let rightShipSize = countConsequtiveCells(this._tableArray, m, n + 1, isDeck, Direction.RIGHT);

        let firstNeighbourSize = 0;
        let secondNeighbourSize = 0;
        if (topShipSize > 0 || bottomShipSize > 0) {
            firstNeighbourSize = topShipSize;
            secondNeighbourSize = bottomShipSize;
        }
        else if (leftShipSize > 0 || rightShipSize > 0) {
            firstNeighbourSize = leftShipSize;
            secondNeighbourSize = rightShipSize;
        }

        return [firstNeighbourSize, secondNeighbourSize];
    }

    _checkUpdatedShipsLeft(shipsLeft) {
        const indices = Array.from(shipsLeft.entries()).filter(pair => pair[1] < 0).map(pair => pair[0]);

        for (let index of indices) {
            if (shipsLeft.slice(index).reduce(
                (accumulator, currentValue) => accumulator + currentValue) < 0) {
                return false;
            }
        }

        return true;
    }
}
