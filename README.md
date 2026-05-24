# Resume AI Backend

Spring Boot backend for ResumeAI. It handles registration/login, resume upload, PDF/DOCX text extraction, Gemini-powered feedback, and follow-up chat.

## Requirements

- Java 17
- PostgreSQL for production
- Gemini API key for feedback/chat

## Environment Variables

Copy `.env.example` for local reference and set these values in Render:

```bash
DB_URL=jdbc:postgresql://your-postgres-host:5432/your_database?sslmode=require
DB_USERNAME=your_database_user
DB_PASSWORD=your_database_password
JWT_SECRET=replace_with_at_least_32_random_characters
JWT_EXPIRATION=86400000
GEMINI_API_KEY=your_gemini_api_key
GEMINI_API_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173,https://resumeai-frontend-rc97.vercel.app
```

## Local Development

Use the dev profile to run with in-memory H2 instead of PostgreSQL:

```bash
./gradlew bootRun -Dspring-boot.run.profiles=dev
```

The backend runs at `http://localhost:8080`.

## API Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/health` | Health check |
| POST | `/auth/register` | Register and receive a JWT |
| POST | `/auth/login` | Login and receive a JWT |
| POST | `/resume/upload` | Upload a PDF/DOCX resume |
| GET | `/resume/history?userId=1` | List a user's resumes |
| GET | `/feedback/{resumeId}` | Get AI feedback |
| POST | `/feedback/chat/{resumeId}` | Ask a follow-up question |

## Deploy to Render

This repo includes `render.yaml` and a Dockerfile. Create a Render Blueprint or Web Service from the repo, then set:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
- `GEMINI_API_KEY`
- `CORS_ALLOWED_ORIGINS` to your Vercel URL: `https://resumeai-frontend-rc97.vercel.app`

Render can generate `JWT_SECRET` from the blueprint.
