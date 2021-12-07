import React from 'react';
import { BrowserRouter as Router, Route, Routes} from 'react-router-dom'
import { AdminModule } from 'App/Admin';
import { SupportModule } from 'App/Support';
import { LoginPage} from 'App/Public';
import { NotFound } from 'App/NotFound';

class App extends React.Component {  
    render() {
        return (
            <Router >
                <Routes>
                    <Route path="/admin/*" element={<AdminModule/>} />
                    <Route path="/support/*" element={<SupportModule/>} />
                    <Route path="/" element={<LoginPage/>} />
                    <Route path="*" element={<NotFound/>}/>
                </Routes>
            </Router>
        );
    }
}

export {App}