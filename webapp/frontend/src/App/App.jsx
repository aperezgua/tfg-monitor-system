import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import { AdminModule } from 'App/Admin';
import { LoginPage} from 'App/Public';
import { NotFound } from 'App/NotFound';

class App extends React.Component {  
    render() {
        return (
            <Router >
                <div>
                    <Routes>
                        <Route path="/admin/*" element={<AdminModule/>} />
                        <Route path="/" element={<LoginPage/>} />
                        <Route path="*" element={<NotFound/>}/>
                    </Routes>
                </div>
            </Router>
        );
    }
}

export {App}