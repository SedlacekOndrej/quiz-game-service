import {Box, Container, Typography} from "@mui/material";

const Home = () => {
    return (
    <>
        <Box sx={{margin: 2}}>
            <Typography sx={{margin: 2}} variant="h3">Vítej ve hře Kvíz!</Typography>
            <Typography sx={{margin: 2}} variant="h3">Zde můžeš ukázat svoji znalost zeměpisu!</Typography>
            <Typography sx={{margin: 2}} variant="h4">Znáš všechna hlavní města států na světě?</Typography>
            <Typography sx={{margin: 2}} variant="h5">V této hře si každý hráč vytvoří účet s úrovní 1 a správnými odpověďmi se bude snažit svou úroveň zvyšovat a tím si odemykat těžší otázky, které mu pomohou svou úroveň zvyšovat rychleji.</Typography>
        </Box>
    </>
    )
}

export default Home;