import {AppBar, Box, Button, Container, Link, Toolbar, Typography} from "@mui/material";
import React from "react";

const Layout = () => {
    const [user, setUser] = React.useState();

    const [isLoggedIn, setIsLoggedIn] = React.useState(false);

    return (
            <Box sx={{flexGrow: 1}}>
            <AppBar position="static">
                <Toolbar>
                    <Typography component="div" variant="h4" sx={{flexGrow: 1}}>
                        Kvíz
                    </Typography>
                    <Button color="inherit" onClick={() => {window.open("/prihlaseni", "_self")}}>Přihlášení</Button>
                  <Button color="inherit" onClick={() => {window.open("/registrace", "_self")}}>Registrace</Button>
                </Toolbar>
            </AppBar>
            </Box>
    )
};

export default Layout;