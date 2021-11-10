import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom'
import { authenticationService } from '_services';
import { HomePage } from 'modules/Admin';
import { LoginPage} from 'modules/Public';
import { NotFound } from 'modules/NotFound';

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
        //this.props.navigate('/');
    }

    render() {
        const { currentUser } = this.state;
        return (
            <Router >
                <div>
                    {currentUser &&
                        <nav className="navbar navbar-expand navbar-dark bg-dark">
                            <div className="navbar-nav">
                                <Link to="/home" className="nav-item nav-link">Home</Link>
                                <Link to="/" onClick={this.logout} className="nav-item nav-link">Logout</Link>
                            </div>
                        </nav>
                    }
                    <div className="jumbotron">
                        <div className="container">
                            <div className="row">
                                <div className="col-md-6 offset-md-3">
                                    <Routes>
	                                    <Route path="/home" element={<HomePage/>} />
                                        <Route path="/" element={<LoginPage/>} />
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

/*function App(props) {
    let navigate = useNavigate();
    return <AppNoNavigate {...props} navigate={navigate} />
}*/
export {App}