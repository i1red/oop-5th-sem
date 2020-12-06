import { CellType, Direction } from './common.js';
import { StrictTableBuilder } from './stricttablebuilder.js';


export function createTableArray() {
    let tableArray = Array.from(Array(10), () => new Array(10));
    tableArray.forEach(row => row.fill(CellType.FREE));
    return tableArray;
}


export function checkDiagonal(tableArray, m, n) {
    if (m > 0 && n > 0 && tableArray[m - 1][n - 1] === CellType.DECK) {
        return false;
    }

    if (m > 0 && n < 9 && tableArray[m - 1][n + 1] === CellType.DECK) {
        return false;
    }

    if (m < 9 && n > 0 && tableArray[m + 1][n - 1] === CellType.DECK) {
        return false;
    }

    if (m < 9 && n < 9 && tableArray[m + 1][n + 1] === CellType.DECK) {
        return false;
    }

    return true;
}


export function countConsequtiveCells(tableArray, m, n, condition, direction) {
    let deckCount = 0;

    if (direction === Direction.UP) {
        for (let i = m; i >= 0; --i) {
            if (condition(tableArray[i][n])) {
                ++deckCount;
            }
            else {
                break;
            }
        }
    }
    else if (direction === Direction.DOWN) {
        for (let i = m; i < 10; ++i) {
            if (condition(tableArray[i][n])) {
                ++deckCount;
            }
            else {
                break;
            }
        }
    }
    else if (direction === Direction.LEFT) {
        for (let i = n; i >= 0; --i) {
            if (condition(tableArray[m][i])) {
                ++deckCount;
            }
            else {
                break;
            }
        }
    }
    else if (direction === Direction.RIGHT) {
        for (let i = n; i < 10; ++i) {
            if (condition(tableArray[m][i])) {
                ++deckCount;
            }
            else {
                break;
            }
        }
    }

    return deckCount;
}


export function shuffle(array) {
    array.sort(() => Math.random() - 0.5);
}


export function createPossibleCellsArray() {
    let possibleCells = [];

    for (let i = 0; i < 10; ++i) {
        for (let j = 0; j < 10; ++j) {
            possibleCells.push([i, j]);
        }
    }

    return possibleCells;
}


export function createRandomlyFilledTable() {
    let tableBuilder = new StrictTableBuilder();

    let possibleCells = createPossibleCellsArray();

    //let usedCells = [];
    let shipSizes = [4, 3, 3, 2, 2, 2, 1, 1, 1, 1];

    while (!tableBuilder.isFilled()) {
        shuffle(possibleCells);

        const shipSize = shipSizes[0];
        const [m, n] = possibleCells.pop();

        let directions = Object.values(Direction);
        shuffle(directions);
        for (const direction of directions) {
            if (tableBuilder.checkAndCreateShip(m, n, shipSize, direction)) {
                shipSizes.shift();
                break;
            }
        }
    }

    return tableBuilder.createTable();
}