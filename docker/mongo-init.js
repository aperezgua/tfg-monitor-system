db.createUser(
        {
            user: "tfg-monitor-system-user",
            pwd: "pw",
            roles: [
                {
                    role: "readWrite",
                    db: "tfg-monitor-system-db"
                }
            ]
        }
);
