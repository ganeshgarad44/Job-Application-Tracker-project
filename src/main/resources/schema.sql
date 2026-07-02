CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS applications (
    id BIGSERIAL PRIMARY KEY,
    company_name VARCHAR(255) NOT NULL,
    role_title VARCHAR(255) NOT NULL,
    status VARCHAR(100) NOT NULL,
    applied_date DATE,
    job_url VARCHAR(500),
    notes TEXT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS interview_rounds (
    id BIGSERIAL PRIMARY KEY,
    round_name VARCHAR(255) NOT NULL,
    interview_date_time TIMESTAMP NOT NULL,
    interview_mode VARCHAR(100) NOT NULL,
    interviewer_name VARCHAR(255) NOT NULL,
    meeting_link VARCHAR(500),
    notes TEXT,
    application_id BIGINT NOT NULL REFERENCES applications(id) ON DELETE CASCADE,
    reminder_sent BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS reminders (
    id BIGSERIAL PRIMARY KEY,
    reminder_type VARCHAR(255) NOT NULL,
    reminder_time TIMESTAMP NOT NULL,
    sent BOOLEAN NOT NULL DEFAULT FALSE,
    message TEXT,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    application_id BIGINT REFERENCES applications(id) ON DELETE SET NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);