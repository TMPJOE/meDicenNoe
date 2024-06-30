import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import './Home.css';

const Home = () => {
    const [owner, setOwner] = useState('');
    const [name, setName] = useState('');
    const history = useHistory();

    const handleSubmit = (e) => {
        e.preventDefault();
        const id = Math.random().toString(36).substr(2, 9); // Generar un id aleatorio
        history.push(`/board/${id}`, { state: { owner, name } });
    };

    return (
        <div className="home-container">
            <nav className="navbar">
                <div className="navbar-brand">Jamboard</div>
                <div className="search-container">
                    <input type="text" placeholder="Búsqueda" className="search-input" />
                </div>
                <div className="navbar-profile">
                    <div className="profile-icon">J</div>
                </div>
            </nav>
            <div className="boards-list">
                <h2>Jams Recientes</h2>
                {/* Aquí se cargarán los tableros */}
            </div>
            <div className="create-board">
                <h2>Crear Nuevo Tablero</h2>
                <form onSubmit={handleSubmit}>
                    <label htmlFor="owner">Autor:</label>
                    <input
                        type="text"
                        id="owner"
                        value={owner}
                        onChange={(e) => setOwner(e.target.value)}
                        required
                    />
                    <label htmlFor="name">Nombre del Tablero:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        required
                    />
                    <button type="submit">Crear</button>
                </form>
            </div>
        </div>
    );
};

export default Home;
