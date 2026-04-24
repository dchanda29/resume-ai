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
