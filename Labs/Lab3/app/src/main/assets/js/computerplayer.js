import { shuffle, ShotResult, createPossibleCellsArray, Direction } from './table/index.js';


export class ComputerPlayer {
    constructor(table) {
        this._table = table;

        this._possibleCells = createPossibleCellsArray();
        shuffle(this._possibleCells);

        this._previousShotResult = null;
        this._nextShotCreator = null;
    }

    shoot() {
        let m, n;
        if (this._nextShotCreator !== null) {
            [m, n] = this._nextShotCreator.nextShot(this._previousShotResult);
            this._possibleCells = this._possibleCells.filter(cell => cell[0] !== m || cell[1] !== n);
        }
        else {
            [m, n] = this._possibleCells.pop();
        }

        let shotData = this._table.shoot(m, n);

        if (shotData.shotResult === ShotResult.DESTROYED) {
            this._possibleCells = this._possibleCells.filter(
                cell => shotData.waterCells.findIndex(
                    waterCell => waterCell[0] === cell[0] && waterCell[1] === cell[1]
                ) === -1
            );

            this._nextShotCreator = null;
        }

        if (shotData.shotResult === ShotResult.DAMAGED) {
            const waterCells = [[m - 1, n - 1], [m - 1, n + 1], [m + 1, n - 1], [m + 1, n + 1]];
            this._possibleCells = this._possibleCells.filter(
                cell => waterCells.findIndex(
                    waterCell => waterCell[0] === cell[0] && waterCell[1] === cell[1]
                ) === -1
            );

            if (this._nextShotCreator === null) {
                this._nextShotCreator = new NextShotCreator(this._possibleCells, [m, n]);
            }
        }

        this._previousShotResult = shotData.shotResult;

        return shotData;
    }

}

class NextShotCreator {
    constructor(possibleCells, damagedDeckCell) {
        let [m, n] = damagedDeckCell;

        this._directionList = [];

        this._topCells = [];
        for (let i = m - 1; i >= m - 3; --i) {
            if (possibleCells.findIndex(cell => cell[0] === i && cell[1] === n) !== -1) {
                this._topCells.push([i, n]);
            }
            else {
                break;
            }
        }
        if (this._topCells.length > 0) {
            this._directionList.push(Direction.UP);
        }

        this._bottomCells = [];
        for (let i = m + 1; i <= m + 3; ++i) {
            if (possibleCells.findIndex(cell => cell[0] === i && cell[1] === n) !== -1) {
                this._bottomCells.push([i, n]);
            }
            else {
                break;
            }
        }
        if (this._bottomCells.length > 0) {
            this._directionList.push(Direction.DOWN);
        }

        this._leftCells = [];
        for (let i = n - 1; i >= n - 3; --i) {
            if (possibleCells.findIndex(cell => cell[0] === m && cell[1] === i) !== -1) {
                this._leftCells.push([m, i]);
            }
            else {
                break;
            }
        }
        if (this._leftCells.length > 0) {
            this._directionList.push(Direction.LEFT);
        }

        this._rightCells = [];
        for (let i = n + 1; i <= n + 3; ++i) {
            if (possibleCells.findIndex(cell => cell[0] === m && cell[1] === i) !== -1) {
                this._rightCells.push([m, i]);
            }
            else {
                break;
            }
        }
        if (this._rightCells.length > 0) {
            this._directionList.push(Direction.RIGHT);
        }

        if (Math.random() < 0.5) {
            this._directionList.reverse();
        }
    }

    nextShot(previousShotResult) {
        let cells = this._chooseCellsList();

        if (previousShotResult === ShotResult.MISSED || cells.length === 0) {
            this._directionList.shift();
            cells = this._chooseCellsList();
        }

        return cells.shift()
    }

    _chooseCellsList() {
        let cells;
        if (this._directionList[0] === Direction.UP) {
            cells = this._topCells;
        }
        else if (this._directionList[0] === Direction.DOWN) {
            cells = this._bottomCells;
        }
        else if (this._directionList[0] === Direction.LEFT) {
            cells = this._leftCells;
        }
        else {
            cells = this._rightCells;
        }

        return cells;
    }
}
