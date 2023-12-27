import React from 'react';
import { useNavigate } from 'react-router-dom';

const LogoutButton = () => {
      localStorage.removeItem('token');
      window.location.href = '/login'; // Redirigez l'utilisateur vers la page de connexion ou une autre page de votre choix
    };

export default LogoutButton;
