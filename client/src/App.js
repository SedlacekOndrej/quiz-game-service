import './App.css';
import Layout from "./components/Layout";
import {BrowserRouter, Route, Router, RouterProvider, Routes, useRoutes} from "react-router-dom";
import {Container, Switch} from "@mui/material";
import QuizRoutes from "./Routes"

const App = () =>
{
  return (
      <>
      <Layout />
          <Container>
            <BrowserRouter>
                <Routes>
                    {QuizRoutes.map(r=>
                    <Route path={r.path} element={r.element}></Route>)}
                </Routes>
            </BrowserRouter>
          </Container>
      </>
  );
}

export default App;