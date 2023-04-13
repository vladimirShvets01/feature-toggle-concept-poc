import React from 'react';
import './App.css';
import RectangularBox from "./components/RectangularBox";
import SendMiddlewareRequestComponent from "./components/SendMiddlewareRequestComponent";
import {useFlag} from "@unleash/proxy-client-react";
import DatabaseSchemaStatus from "./components/DatabaseSchemaStatus";

function App() {
  return (
    <div className="App">
      <div>
        <RectangularBox orderNumber={1}></RectangularBox>
        <RectangularBox orderNumber={2}></RectangularBox>
        <RectangularBox orderNumber={3}></RectangularBox>
        <RectangularBox orderNumber={4}></RectangularBox>
        <RectangularBox orderNumber={5}></RectangularBox>
        <RectangularBox orderNumber={6}></RectangularBox>
        {
          useFlag('feature-flag-1') && <p>You are seeing this message, because flag 1 is enabled</p>
        }
        {/*<p>some text for github actions test</p>*/}
        <div className="requests">
          <SendMiddlewareRequestComponent></SendMiddlewareRequestComponent>
          <DatabaseSchemaStatus></DatabaseSchemaStatus>
        </div>
      </div>
    </div>
  );
}

export default App;
