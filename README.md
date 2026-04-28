# Resume AI — Backend

Spring Boot backend for the Resume AI web app.  
Handles auth, resume upload, AI-powered analysis (Gemini), and follow-up chat.

---

## Tech Stack

- **Java 17** + Spring Boot 3.2
- **PostgreSQL** (via Supabase or local)
- **Google Gemini API** (free tier)
- **Apache PDFBox** — PDF parsing
- **Apache POI** — DOCX parsing
- **JWT** — stateless auth

---

## API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | ❌ | Register with name, email/phone, password |
| POST | `/api/auth/login` | ❌ | Login, returns JWT token |
| GET | `/api/user/me` | ✅ | Get current user profile |
| POST | `/api/resume/upload` | ✅ | Upload PDF or DOCX — triggers AI analysis |
| GET | `/api/resume/history` | ✅ | List all uploaded resumes |
| GET | `/api/feedback/{resumeId}` | ✅ | Get AI analysis for a resume |
| POST | `/api/chat/{resumeId}` | ✅ | Send a follow-up question about the resume |
| GET | `/api/chat/{resumeId}/history` | ✅ | Get full chat history for a resume |

---

## Setup

### 1. Clone the repo
```bash
git clone https://github.com/dchanda29/resume-ai.git
cd resume-ai
```

### 2. Set environment variables
Copy `.env.example` to `.env` and fill in the values:
```bash
cp .env.example .env
```

### 3. Get a Gemini API key (free)
Go to https://aistudio.google.com → Get API Key → copy it to your `.env`

### Gemini API usage
The app uses Google Gemini for AI-powered resume analysis and chat. If you don't set `GEMINI_API_KEY`, the application will still run (or you can run with the `dev` profile), but any endpoints that rely on Gemini (feedback, chat) will be disabled or return errors.

To enable Gemini features, set the environment variable:

```powershell
$env:GEMINI_API_KEY = 'your_key_here'
```

On production hosts (e.g., Render), add `GEMINI_API_KEY` in the service's environment settings.

### 4. Set up PostgreSQL
Use Supabase (free) or run locally:
```bash
docker run --name resumeai-db -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=resumeai -p 5432:5432 -d postgres
```

### 5. Run the app
```bash
./gradlew bootRun
```

The API is now running at `http://localhost:8080`

### Local development with H2 (no Postgres required)

You can run the application using an in-memory H2 database for quick local development and testing.

Run with the `dev` Spring profile (enables H2):
```bash
./gradlew bootRun -Dspring-boot.run.profiles=dev
```

Or set the environment variable:
```powershell
#$env:SPRING_PROFILES_ACTIVE = 'dev'
./gradlew bootRun
```

Access the H2 console at `http://localhost:8080/h2-console` and use JDBC URL `jdbc:h2:mem:resumeai`, user `sa` with no password.

When you're ready to use Postgres again, unset the profile or run without `dev` and set `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` as described above.

---

## Test with curl

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Dev","email":"dev@test.com","password":"test1234"}'

# Upload resume (use token from register response)
curl -X POST http://localhost:8080/api/resume/upload \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "file=@/path/to/resume.pdf"

# Get feedback (use resumeId from upload response)
curl http://localhost:8080/api/feedback/RESUME_ID \
  -H "Authorization: Bearer YOUR_TOKEN"

# Chat
curl -X POST http://localhost:8080/api/chat/RESUME_ID \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"message":"Is my resume good for a backend engineering role at a startup?"}'
```

---

## Deploy to Render (free)

1. Push to GitHub
2. Go to render.com → New Web Service → connect your repo
3. Set environment variables in Render dashboard
4. Build command: `./gradlew build`
5. Start command: `java -jar build/libs/resume-ai-0.0.1-SNAPSHOT.jar`

---

## Project Structure

```
src/main/java/com/resumeai/
├── auth/          # JWT auth, register, login
├── user/          # User profile
├── resume/        # Upload, parse PDF/DOCX
├── feedback/      # Gemini static analysis
├── chat/          # Gemini follow-up chat
├── config/        # Security, CORS, Async
└── common/        # Exception handler
```
