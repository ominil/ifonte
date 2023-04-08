import {
    Button,
    Drawer,
    DrawerBody,
    DrawerCloseButton,
    DrawerContent, DrawerFooter,
    DrawerHeader,
    DrawerOverlay,
    useDisclosure
} from "@chakra-ui/react";
import CreateEmployerForm from "./CreateEmployerForm.jsx";

const AddIcon = () => "+"

const CreateEmployerDrawer = ({fetchEmployers}) => {

    const { isOpen, onOpen, onClose } = useDisclosure()

    return (
        <>
            <Button
                leftIcon={<AddIcon/>}
                colorScheme={"blue"}
                onClick={onOpen}

            >
                Create Employer
            </Button>
            <Drawer isOpen={isOpen} onClose={onClose} size={"lg"}>
                <DrawerOverlay />
                <DrawerContent>
                    <DrawerCloseButton />
                    <DrawerHeader>Create your employer</DrawerHeader>

                    <DrawerBody>
                        <CreateEmployerForm
                            fetchEmployers={fetchEmployers}
                        />
                    </DrawerBody>

                    <DrawerFooter>
                    </DrawerFooter>
                </DrawerContent>
            </Drawer>
        </>
    )
}

export default CreateEmployerDrawer;