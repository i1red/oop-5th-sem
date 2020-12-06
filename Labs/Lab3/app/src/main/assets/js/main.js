import { Game } from './game.js'


document.getElementById('restart').addEventListener('click', () => {
    new Game();
});

new Game();
