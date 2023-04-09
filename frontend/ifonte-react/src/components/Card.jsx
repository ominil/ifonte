import { useRef } from 'react';
import {
    Heading,
    Box,
    Center,
    Text,
    Stack,
    Button,
    useColorModeValue,
    Tag,
    useDisclosure,
    AlertDialogOverlay,
    AlertDialogContent,
    AlertDialogHeader,
    AlertDialog,
    AlertDialogBody, AlertDialogFooter,
} from '@chakra-ui/react';
import {deleteEmployer} from "../services/client.js";
import {errorNotification, successNotification} from "../services/notification.js";
import UpdateEmployerDrawer from "./UpdateEmployerDrawer.jsx";

const Card = ({id, name, email, fetchEmployers}) => {

    const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = useRef()

    return (
        <Center py={6}>
            <Box
                maxW={'300px'}
                minW={'300px'}
                w={'full'}
                m={2}
                bg={useColorModeValue('white', 'gray.800')}
                boxShadow={'lg'}
                rounded={'md'}
                overflow={'hidden'}
            >
                <Box p={6}>
                    <Stack spacing={2} align={'center'} mb={5}>
                        <Tag>{id}</Tag>
                        <Heading fontSize={'2xl'} fontWeight={500} fontFamily={'body'}>
                            {name}
                        </Heading>
                        <Text color={'gray.500'}>{email}</Text>
                    </Stack>
                </Box>
                <Stack direction={'row'} justify={'center'} spacing={6} p={4}>
                    <Stack>
                        <UpdateEmployerDrawer
                            initialValue={{name, email}}
                            employerId={id}
                            fetchEmployers={fetchEmployers}
                        />
                    </Stack>
                    <Stack >
                        <Button
                            bg={'red.500'}
                            color={'white'}
                            rounded={'full'}
                            _hover={{
                                transforms: 'translateY(-2px)',
                                boxShadow: 'lg'
                            }}
                            _focus={{
                                bg: 'grey.500'
                            }}
                            onClick={onOpen}
                        >
                            Delete
                        </Button>
                        <AlertDialog
                            isOpen={isOpen}
                            leastDestructiveRef={cancelRef}
                            onClose={onClose}
                        >
                            <AlertDialogOverlay>
                                <AlertDialogContent>
                                    <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                                        Delete Employer
                                    </AlertDialogHeader>

                                    <AlertDialogBody>
                                        Are you sure you want to delete {name}? You can't undo this action afterwards.
                                    </AlertDialogBody>

                                    <AlertDialogFooter>
                                        <Button ref={cancelRef} onClick={onClose}>
                                            Cancel
                                        </Button>
                                        <Button colorScheme='red' onClick={() => {
                                            deleteEmployer(id).then(() => {
                                                successNotification(
                                                    'Employer Deleted',
                                                    `${name} was successfully deleted`
                                                )
                                                fetchEmployers()
                                            }).catch(err => {
                                                console.log(err)
                                                errorNotification(
                                                    err.code,
                                                    err.response?.data.message || err.message
                                                )
                                            }).finally(
                                                onClose()
                                            )
                                        }
                                        } ml={3}>
                                            Delete
                                        </Button>
                                    </AlertDialogFooter>
                                </AlertDialogContent>
                            </AlertDialogOverlay>
                        </AlertDialog>
                    </Stack>
                </Stack>
            </Box>
        </Center>
    );
}

export default Card;