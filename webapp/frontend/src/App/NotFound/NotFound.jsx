import React from 'react';

/** Componente que es llamado desde el enrutador principal cuando no se encuentra la página. **/
class NotFound extends React.Component {
    render() {
        return (
            <div className="alert alert-error">
                No se ha encontrado la página.
            </div>
        )
    }
}

export { NotFound }; 