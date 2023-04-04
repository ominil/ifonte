import {Wrap, WrapItem, Spinner, Text} from '@chakra-ui/react'
import SimpleSidebar from "./components/shared/SideBar.jsx";
import {getEmployers} from "./services/client.js";
import { useEffect, useState } from "react";
import Card from "./components/Card.jsx";

const App = () => {

    const [employers, setEmployers] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        getEmployers().then(res => {
            setEmployers(res.data)
        }).catch(err => {
            console.log(err)
        }).finally( () => setLoading(false))
    }, [])

    if (loading) {
        return (
            <SimpleSidebar>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SimpleSidebar>
        )
    }

    if (employers.length <= 0) {
        return (
            <SimpleSidebar>
                <Text>No employers Available</Text>
            </SimpleSidebar>
        )
    }

    return(
        <SimpleSidebar>
            <Wrap spacing={'30px'} justify={'center'}>
                {employers.map((employer, index) => (
                    <WrapItem>
                        <Card key={index} {...employer}></Card>
                    </WrapItem>
                ))}
            </Wrap>
        </SimpleSidebar>

    )
}

export default App;