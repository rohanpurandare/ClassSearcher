import React, { useState, validated } from "react";
import { Col, Row, Form, Button, Dropdown } from "react-bootstrap";
import "./App.css";


function App() {
    const [major, setMajor] = useState();
    const [option, setOp] = useState([]);
    const [subject, setSubject] = useState();
    const [tag, setTag] = useState();

    const handleSubmit = (e) => {
        const form = e.currentTarget;
        e.preventDefault();
        console.log(major,option, subject, tag);
        
        if (option == 1) {
            
        } else {

        }
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

                        <Form.Group as={Row}>
                            <Form.Label as="legend" column sm={2}>
                                Options
                            </Form.Label>
                            <Col sm={10}>
                                <Form.Check
                                    type="radio"
                                    label="Search by subject"
                                    name="formHorizontalRadios"
                                    id="formHorizontalRadios1"
                                    onChange={(e) => setOp(1)}
                                    
                                />
                                <Form.Check
                                    type="radio"
                                    label="Search by tag"
                                    name="formHorizontalRadios"
                                    id="formHorizontalRadios2"
                                    onChange={(e) => setOp(2)}
                                
                                />
                                
                            </Col>
                        </Form.Group>
                        <Form.Row>
                        <Form.Group as={Col} controlId="validationCustom01">
                            <Form.Label>Subject</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Subject"
                                onChange={(e) => setSubject(e.target.value)}
                            />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} controlId="validationCustom01">
                            <Form.Label>Tag</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Tag"
                                onChange={(e) => setTag(e.target.value)}
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
