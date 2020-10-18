import React, { useState, validated} from "react";
import { Col, Form, Button, Dropdown } from "react-bootstrap";
import "./App.css";


function App() {
    const [major, setMajor] = useState();
    const [minor, setMinor] = useState();

    const handleSubmit = (e) => {
        const form = e.currentTarget;
        e.preventDefault();
        console.log(major, minor);
    }

    return (
        <div className="whole">
            <div className="top-block">
                <h1 className="message">Welcome</h1>
            </div>
            <div className="bottom-block">
                <Form
                id="responses"
                noValidate
                validated={validated}
                onSubmit={handleSubmit}>
                    <Form.Row>
                        <Form.Group as={Col} controlId="validationCustom01">
                            <Form.Label>Major</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Major"
                                onChange={(e) => setMajor(e.target.value)}
                            />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} controlId="validationCustom01">
                            <Form.Label>Minor</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Minor"
                                onChange={(e) => setMinor(e.target.value)}
                            />
                        </Form.Group>
                    </Form.Row>
                    <Button variant="light" className="myButton" type="submit">Get results</Button>
                </Form>

            </div>
        </div>
    );
}

export default App;
