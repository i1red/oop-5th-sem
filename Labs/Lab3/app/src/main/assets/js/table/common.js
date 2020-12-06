export const CellType = {
    FREE: 0,
    DECK: 1,
    DAMAGED_DECK: 2,
};
Object.freeze(CellType);


export const Direction = {
    UP: 0,
    DOWN: 1,
    LEFT: 2,
    RIGHT: 3,
}
Object.freeze(Direction);


export const ShotResult = {
    MISSED: 0,
    DAMAGED: 1,
    DESTROYED: 2,
};
Object.freeze(ShotResult);


export class ShotData {
    constructor(cellCoordinates, shotResult, waterCells) {
        this.cellCoordinates = cellCoordinates;
        this.shotResult = shotResult;
        this.waterCells = waterCells;
    }
}
