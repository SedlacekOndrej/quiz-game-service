import {Button, FormControl, TextField} from "@mui/material";
import React from "react";

const Login = () => {
    const [inputs, setInputs] = React.useState({username: null, password: null});

    const updateInput = (event) => {
    let value = event.target.value;

    switch (event.target.name) {
        case "username":
            inputs.username = value;
            break;
        case "password":
            inputs.password = value;
            break;
    }
}

    const submit = () => {
        const submitData = async (data) => {
            let json = JSON.stringify(data);

            let response =  await fetch("http://localhost:8080/api/user/login", {method: "POST", body: json, headers: {
                    'Content-Type': 'application/json'
                },});
            return response.json();
        }
        submitData(inputs).then(result => {
            alert("Received result!");
            console.log(result);
        });
    }


return (
    <>
        <h1>Přihlásit se</h1>
        <FormControl>
            <TextField onChange={updateInput} id="username" name="username" label="Uživatelské jméno" title="Zadejte své požadované uživatelské jméno."></TextField>
            <TextField sx={{margin:{top: 40}}} type="password" onChange={updateInput} id="password" name="password" label="Heslo" title="Zadejte heslo po váš uživatelský účet."></TextField>
            <Button sx={{margin:{top: 80}}} onClick={submit} id="submit" name="submit" variant="contained">Přihlásit</Button>
        </FormControl>
    </>
)
};

export default Login;