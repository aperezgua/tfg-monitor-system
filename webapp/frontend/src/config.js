let configuration = {};
if (!process.env.NODE_ENV || process.env.NODE_ENV === 'development') {
    configuration= {
        apiAuthUrl: 'http://localhost:8091',
        apiUserUrl: 'http://localhost:8092',
        apiSystemUrl: 'http://localhost:8093',
        apiAgentUrl: 'http://localhost:8094',
        apiLogUrl: 'http://localhost:8095'
    };    
} else {
    configuration= {
        apiAuthUrl: 'http://@server.host@:8091',
        apiUserUrl: 'http://@server.host@:8092',
        apiSystemUrl: 'http://@server.host@:8093',
        apiAgentUrl: 'http://@server.host@:8094',
        apiLogUrl: 'http://@server.host@:8095'
    };    
}

const config = configuration;

export default config;