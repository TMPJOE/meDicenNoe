import React from "react";
import { HashRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./components/Home";
import Board from "./components/Board";
import "./App.css";

function App() {
  return (
    <Router>
      <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/board/:id" component={Board} />
      </Switch>
    </Router>
  );
}

export default App;
