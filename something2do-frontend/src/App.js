import {Button, Col, ListGroup, Row, Tab, Tabs} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import {useEffect, useState} from "react";
import axios from "axios";

function App() {
    const [users, setUsers] = useState([]);
    const [uncompletedTasks, setUncompletedTasks] = useState([])
    const [completedTasks, setCompletedTasks] = useState([])
    const [inWishListTasks, setInWishListTasks] = useState([])
    const [day, setDay] = useState()
    const [dayTask, setDayTask] = useState()
    const [selectedUsername, setSelectedUsername] = useState()


    useEffect(() => {
        axios.get('http://localhost:8080/day')
            .then(response => {
                setDay(response.data)
                axios.get('http://localhost:8080/task-of-the-day')
                    .then(response => {
                        setDayTask(response.data)
                        axios.get('http://localhost:8080/users')
                            .then(response => {
                                setUsers(response.data)
                                fetchTaskByUsername(response.data[0].username)
                            })
                            .catch(error => console.error(error));
                    })
                    .catch(error => console.error(error));
            })
            .catch(error => console.error(error));


    }, []);


    function fetchUncompletedTasksByUsername(username) {
        console.log("fetchUncompletedTasks " + username)

        axios.get(`http://localhost:8080/uncompleted-tasks/${username}`)
            .then(response => setUncompletedTasks(response.data))
            .catch(error => console.error(error));
    }

    function fetchCompletedTasksByUsername(username) {
        console.log("fetchCompletedTasks " + username)

        axios.get(`http://localhost:8080/completed-tasks/${username}`)
            .then(response => setCompletedTasks(response.data))
            .catch(error => console.error(error));
    }

    function fetchWishListTasksByUsername(username) {
        console.log("fetchWishListTasks " + username)

        axios.get(`http://localhost:8080/inwishlist-tasks/${username}`)
            .then(response => setInWishListTasks(response.data))
            .catch(error => console.error(error));


    }


    function fetchTaskByUsername(username) {
        setSelectedUsername(username);
        fetchUncompletedTasksByUsername(username);
        fetchCompletedTasksByUsername(username);
        fetchWishListTasksByUsername(username);
    }


    function completeTaskFromUncompleted(key, username) {
        axios.post(`http://localhost:8080/markascomplete/${username}`, null, {
            params: {key}
        }).then(r => {
            fetchUncompletedTasksByUsername(username);
            fetchCompletedTasksByUsername(username);
        })
    }

    function completeTaskFromWishList(key, username) {

        axios.post(`http://localhost:8080/markascomplete/${username}`, null, {
            params: {key}
        }).then(r => {
            fetchWishListTasksByUsername(username);
            fetchCompletedTasksByUsername(username);
        })

    }

    function addToWishList(key, username) {
        axios.post(`http://localhost:8080/addtasktowishlist/${username}`, null, {
            params: {key}
        }).then(r => {
            fetchUncompletedTasksByUsername(username);
            fetchWishListTasksByUsername(username);
        })
    }

    function incrementDay() {
        axios.get('http://localhost:8080/incrementDay')
            .then(response => {
                setDay(response.data)
                axios.get('http://localhost:8080/task-of-the-day')
                    .then(response => {
                        setDayTask(response.data)
                        fetchTaskByUsername(selectedUsername)

                    })
                    .catch(error => console.error(error));
            })
            .catch(error => console.error(error));
    }

    return (<div>
        <div className="fw-bold"> Day {day}. <Button onClick={() => incrementDay()}>Next day</Button></div>


        <Tabs id="uncontrolled-tab-example" className="mb-3" onSelect={(username) => fetchTaskByUsername(username)}>
            {users.map(user => (<Tab eventKey={user.username} title={user.username}>

                <Row>

                    <ListGroup as={Col} className="h-25" title="Helo">
                        <div className="alert alert-primary fs-1 text-center fw-bold" role="alert">
                            Uncompleted Tasks
                        </div>

                        {uncompletedTasks.filter(task => task.key === dayTask.key).map(task => (<ListGroup.Item>
                            <Row>
                                <div className="fw-bold fs-4 text-primary">
                                    <span>This is task of the day</span>
                                </div>
                                <div className="fw-bold">
                                    <span>{task.activity}</span>
                                </div>
                                <span>Rank: {task.rank}</span>
                                <span>Type: {task.type}</span>
                                <Button
                                    onClick={() => completeTaskFromUncompleted(task.key, user.username)}>Complete</Button>
                                <Button
                                    onClick={() => addToWishList(task.key, user.username)}>Add to
                                    WishList</Button>

                            </Row>
                        </ListGroup.Item>))}

                        {uncompletedTasks.filter(task => task.key !== dayTask.key).map(task => (<ListGroup.Item>
                            <Row>
                                <div className="fw-bold">
                                    <span>{task.activity}</span>
                                </div>
                                <span>Rank: {task.rank}</span>
                                <span>Type: {task.type}</span>
                                <Button
                                    onClick={() => completeTaskFromUncompleted(task.key, user.username)}>Complete</Button>
                                <Button
                                    onClick={() => addToWishList(task.key, user.username)}>Add to
                                    WishList</Button>

                            </Row>
                        </ListGroup.Item>))}
                    </ListGroup>


                    <ListGroup as={Col} className="h-25">
                        <div className="alert alert-primary fs-1 text-center fw-bold" role="alert">
                            Completed Tasks
                        </div>

                        {inWishListTasks.filter(task => task.key === dayTask.key).map(task =>
                            <ListGroup.Item>

                                <Row>
                                    <div className="fw-bold fs-4 text-primary">
                                        <span>This is task of the day</span>
                                    </div>
                                    <div className="fw-bold">
                                        <span>{task.activity}</span>
                                    </div>
                                    <span>Rank: {task.rank}</span>
                                    <span>Type: {task.type}</span>
                                    <Button
                                        onClick={() => completeTaskFromWishList(task.key, user.username)}>Complete</Button>

                                </Row>
                            </ListGroup.Item>
                        )}


                        {inWishListTasks.filter(task => task.key !== dayTask.key).map(task => (
                            <ListGroup.Item>

                                <Row>
                                    <div className="fw-bold">
                                        <span>{task.activity}</span>
                                    </div>
                                    <span>Rank: {task.rank}</span>
                                    <span>Type: {task.type}</span>
                                    <Button
                                        onClick={() => completeTaskFromWishList(task.key, user.username)}>Complete</Button>

                                </Row>
                            </ListGroup.Item>

                        ))}
                    </ListGroup>


                    <ListGroup as={Col} className="h-25">
                        <div className="alert alert-primary fs-1 text-center fw-bold" role="alert">
                            Completed Tasks
                        </div>

                        {completedTasks.filter(task => task.key === dayTask.key).map(task =>
                            <ListGroup.Item>

                                <Row>
                                    <div className="fw-bold fs-4 text-primary">
                                        <span>This is task of the day</span>
                                    </div>
                                    <div className="fw-bold">
                                        <span>{task.activity}</span>
                                    </div>
                                    <span>Rank: {task.rank}</span>
                                    <span>Type: {task.type}</span>
                                </Row>
                            </ListGroup.Item>
                        )}


                        {completedTasks.filter(task => task.key !== dayTask.key).map(task => (
                            <ListGroup.Item>

                                <Row>
                                    <div className="fw-bold">
                                        <span>{task.activity}</span>
                                    </div>
                                    <span>Rank: {task.rank}</span>
                                    <span>Type: {task.type}</span>
                                </Row>
                            </ListGroup.Item>

                        ))}
                    </ListGroup>

                </Row>

            </Tab>))}

        </Tabs>

    </div>);
}

export default App;
