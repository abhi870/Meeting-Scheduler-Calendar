CREATE TABLE meeting(
    meeting_id UUID PRIMARY KEY,
    start_time TIMESTAMP,
    end_time TIMESTAMP
);

CREATE TABLE app_user(
    user_id UUID PRIMARY KEY,
    first_name VARCHAR(30),
    last_name VARCHAR(30)
);

CREATE TABLE users_meetings(
    composite_id UUID PRIMARY KEY,
    user_id UUID,
    meeting_id UUID,
    CONSTRAINT user_id FOREIGN KEY(user_id) REFERENCES app_user(user_id),
    CONSTRAINT meeting_id FOREIGN KEY(meeting_id) REFERENCES meeting(meeting_id)
);
