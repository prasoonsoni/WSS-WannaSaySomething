import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import UserPage from './pages/UserPage';
import HomePage from './pages/HomePage'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path='/:username' element={<UserPage />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);
