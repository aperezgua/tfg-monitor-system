import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'

import { history } from '_helpers';
import { authenticationService } from '_services';
import { HomePage } from 'HomePage';
import { LoginPage } from 'LoginPage';
import { NotFound } from 'NotFound';

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentUser: null
        };
    }

    componentDidMount() {
        authenticationService.currentUser.subscribe(x => this.setState({ currentUser: x }));
    }

    logout() {
        authenticationService.logout();
        history.push('/login');
    }

    render() {
        const { currentUser } = this.state;
        return (
            <Router history={history}>
                <div>
                    {currentUser &&
                        <nav className="navbar navbar-expand navbar-dark bg-dark">
                            <div className="navbar-nav">
                                <Link to="/" className="nav-item nav-link">Home</Link>
                                <a onClick={this.logout} className="nav-item nav-link">Logout</a>
                            </div>
                        </nav>
                    }
                    <div className="jumbotron">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-6 offset-md-3">
                                    <Routes>                                
	                                    <Route exact path="/" element={<HomePage/>} />
                                        <Route path="/login" element={<LoginPage/>} />
                                        <Route path="*" element={<NotFound/>}/>
                                    </Routes>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </Router>
        );
    }
}

export { App }; 