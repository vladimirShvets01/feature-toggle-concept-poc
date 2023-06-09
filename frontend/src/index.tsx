import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import FlagProvider from "@unleash/proxy-client-react";

const host = process.env.REACT_APP_HOST;

const config = {
    url: 'http://' + host + ':4242/api/frontend',
    clientKey: 'default:development.unleash-insecure-frontend-api-token',
    refreshInterval: 15,
    appName: 'feature-flag-example'
};


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <FlagProvider config={config}>
            <App/>
        </FlagProvider>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
