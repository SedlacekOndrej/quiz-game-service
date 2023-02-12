import {Alert, Button, FormControl, Grid, Snackbar, TextField} from "@mui/material";
import React, {useEffect} from "react";
const RegisterUser = () => {
    const [inputs, setInputs] = React.useState({username: null, password: null, email: null, passwordConfirm: null});

    const updateInput = (event) => {
        let value = event.target.value;

        switch (event.target.name) {
            case "username":
                inputs.username = value;
                break;
            case "password":
                inputs.password = value;
                break;
            case "email":
                inputs.email = value;
                break;
            case "passwordConfirm":
                inputs.passwordConfirm = value;
        }
    }

    const [displayError, setDisplayError] = React.useState(false);

    const toastNotification = <><Snackbar anchorOrigin={{vertical: "bottom", horizontal: "center"}} open={displayError} sev autoHideDuration={5000}>
        <Alert severity="error">Musíte vyplnit všechna pole.</Alert>
    </Snackbar></>;

    useEffect(() => {
        if (displayError) {
            setTimeout(() => {
                setDisplayError(false)
            }, 5000)
        }
    }, [displayError]);

    const submit = () => {
        if ((!inputs.email) || (!inputs.username) || (!inputs.password) || (!inputs.passwordConfirm)) {
            setDisplayError(true);
            return;
        }

        const submitData = async (data) => {
            let json = JSON.stringify(data);

           let response =  await fetch("http://localhost:8080/api/user/registration", {method: "POST", body: json, headers: {
                   'Content-Type': 'application/json'
               },});
           return response.json();
        }

        submitData(inputs).then(result => {
            console.log(result);
        });
    }

    return (
        <>
        <h1>Registrace</h1>
        <FormControl>
            <TextField onChange={updateInput} id="username" name="username" label="Uživatelské jméno" title="Zadejte své požadované uživatelské jméno."></TextField>
            <TextField sx={{margin:{top: 20}}} onChange={updateInput} id="email" name="email" label="E-mailová adresa" title="Zadejte svůj email."></TextField>
            <TextField sx={{margin:{top: 40}}} type="password" onChange={updateInput} id="password" name="password" label="Heslo" title="Zadejte heslo po váš uživatelský účet."></TextField>
            <TextField sx={{margin:{top: 60}}} type="password" onChange={updateInput} id="passwordConfirm" name="passwordConfirm" label="Zopakujte své heslo" title="Zadejte znovu vaše heslo pro potvrzení."></TextField>
            <Button sx={{margin:{top: 80}}} onClick={submit} id="submit" name="submit" variant="contained">Registrovat</Button>
        </FormControl>
            {toastNotification}
        </>
    )
}

export default RegisterUser;