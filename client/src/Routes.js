import React from "react";
import Home from "./components/Home";
import RegisterUser from "./components/RegisterUser";
import Login from "./components/Login";

const QuizRoutes = [
    {
        path: "/",
        element: <Home/>
    },
    {
        path: "/registrace",
        element: <RegisterUser/>
    },
    {
        path: "/prihlaseni",
        element: <Login/>
    }
];

export default QuizRoutes;