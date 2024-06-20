import React, { useState, useEffect } from 'react';
import './BoardList.css';

const BoardList = () => {
  const [boards, setBoards] = useState([]);

  useEffect(() => {
    fetch('http://localhost:8080/api/boards')
      .then(response => response.json())
      .then(data => setBoards(data))
      .catch(error => console.error('Error fetching boards:', error));
  }, []);

  return (
    <div className="board-list">
      <h2>Jams Recientes</h2>
      <div className="board-grid">
        {boards.map(board => (
          <div key={board.id} className="board-card">
            <a href={`/tablero.html?id=${board.id}`}>
              <img src="B.png" alt="Board Thumbnail" className="board-thumbnail" />
              <div className="board-info">
                <h3>{board.name}</h3>
                <p>{new Date(board.createdAt).toLocaleDateString()}</p>
              </div>
            </a>
          </div>
        ))}
      </div>
    </div>
  );
};

export default BoardList;
