import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import { authenticationService } from '_services';
import { HomePage } from 'App/Admin';
import { LoginPage} from 'App/Public';
import { NotFound } from 'App/NotFound';

class App extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentToken: null
        };
    }

    componentDidMount() {
        authenticationService.currentToken.subscribe(x => this.setState({ currentToken: x }));
    }

    logout() {
        authenticationService.logout();
        //this.props.navigate('/');
    }

    render() {
        const { currentToken } = this.state;
        return (
            <Router >
                <div>
                    <Routes>
                        <Route path="/home" element={<HomePage/>} />
                        <Route path="/" element={<LoginPage/>} />
                        <Route path="*" element={<NotFound/>}/>
                    </Routes>
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