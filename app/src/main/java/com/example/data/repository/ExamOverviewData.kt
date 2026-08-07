package com.example.data.repository

import com.example.data.models.AppConstants

data class ExamSection(
    val number: String,
    val title: String,
    val summary: String,
    val content: List<String>,
    val keyPoints: List<String> = emptyList()
)

object ExamOverviewData {
    val noteOnQuestionCount: String =
        "Note on Structure & Question Count: Official Ministry guidelines state a standard 100-question baseline (60 Verbal, 40 Quantitative), while recent actual national administrations (2023–2025) feature 125 questions including 25 Analytical Reasoning items within a comprehensive 180-minute block. This preparation suite covers all 125-question formats and 80 journey levels."

    val sections: List<ExamSection> = listOf(
        ExamSection(
            number = "I",
            title = "What the Exam Is",
            summary = "The National Graduate Admission Test (NGAT / GAT) is Ethiopia's standardized entrance examination for postgraduate admissions.",
            content = listOf(
                "The National Graduate Admission Test (NGAT / GAT) is the mandatory standardized assessment administered across Ethiopian public and private higher education institutions.",
                "It serves as the objective national benchmark to measure general academic aptitude, critical thinking, problem-solving, and verbal reasoning skills required for Master's and Doctoral degree programs."
            ),
            keyPoints = listOf(
                "Mandatory prerequisite for postgraduate admission in Ethiopia.",
                "Measures readiness for graduate-level research and coursework.",
                "Administered under the supervision of the Ministry of Education (MoE)."
            )
        ),
        ExamSection(
            number = "II",
            title = "Test Objectives & Scope",
            summary = "Evaluates higher-order cognitive capabilities rather than memorization of specific undergraduate subjects.",
            content = listOf(
                "The primary objective of the NGAT is to evaluate general intellectual ability rather than specific undergraduate field knowledge.",
                "It assesses analytical acumen, quantitative fluency, reading comprehension depth, logical consistency, and linguistic precision."
            ),
            keyPoints = listOf(
                "Aptitude-based rather than discipline-specific memorization.",
                "Equal playing field across diverse academic undergraduate backgrounds.",
                "Emphasizes logical deduction, data interpretation, and semantic mastery."
            )
        ),
        ExamSection(
            number = "III",
            title = "Eligibility & Target Audience",
            summary = "All candidates applying for postgraduate (MSc, MA, MBA, PhD, MD specialty) programs.",
            content = listOf(
                "Graduates holding an accredited Bachelor's degree (or equivalent) seeking admission into Ethiopian postgraduate programs are required to sit for the NGAT.",
                "Candidates may sit for the exam at designated national testing centers, including Addis Ababa University (AAU) and regional public universities."
            ),
            keyPoints = listOf(
                "Bachelor's degree holders or final-year prospective graduates.",
                "Applicable to both regular, extension, weekend, and summer programs.",
                "Valid across universities accepting the national standard score."
            )
        ),
        ExamSection(
            number = "IV",
            title = "Registration, Fees & Schedule",
            summary = "Official online portal registration and designated testing windows throughout the academic year.",
            content = listOf(
                "Registration is conducted through official institutional and national portals prior to admission intake cycles.",
                "Candidates must pay the designated registration fee through digital payment channels (such as Telebirr, CBE Birr, or direct bank transfer) and receive their test admission ticket with assigned center details."
            ),
            keyPoints = listOf(
                "Multiple test cycles per year ahead of semester intakes.",
                "Digital fee payment required before ticket generation.",
                "Admission ticket and valid national ID / passport required on test day."
            )
        ),
        ExamSection(
            number = "V",
            title = "Exam Format & Time Allocation",
            summary = "Standard computerized or paper-based multi-section assessment spanning 180 minutes.",
            content = listOf(
                "The examination is delivered as a timed multiple-choice assessment consisting of three primary sections:",
                "1. Verbal Reasoning: 60 questions (Vocabulary in Context, Sentence Equivalence, Reading Comprehension, Analogies, Error Identification).",
                "2. Quantitative Reasoning: 40 questions (Arithmetic, Algebra, Geometry, Data Interpretation, Quantitative Comparison, Data Sufficiency).",
                "3. Analytical Reasoning: 25 questions (Logical Deduction, Seating Arrangements, Pattern & Sequence Reasoning)."
            ),
            keyPoints = listOf(
                "Total Duration: 180 minutes (3 hours).",
                "Verbal Section: ~60 seconds per item recommended pace.",
                "Quantitative Section: ~90 seconds per item recommended pace.",
                "Analytical Section: ~144 seconds per item recommended pace."
            )
        ),
        ExamSection(
            number = "VI",
            title = "Scoring System & Cutoff Thresholds",
            summary = "Standard scoring with a national 50% baseline pass mark.",
            content = listOf(
                "Each correctly answered question awards positive credit. There is no negative marking / penalty for incorrect guesses, making it advantageous to attempt every item.",
                "The national benchmark passing threshold is generally established at 50% (e.g., 50/100 or 62.5/125 raw points), though competitive departments may impose higher cutoffs."
            ),
            keyPoints = listOf(
                "No negative marking: never leave an item blank.",
                "National pass threshold: 50% aggregate score.",
                "Departmental cutoffs for high-demand fields (Medicine, Engineering, Law, MBA) may range from 60% to 75%+."
            )
        ),
        ExamSection(
            number = "VII",
            title = "Section Breakdown & Topic Weights",
            summary = "Detailed cognitive distribution across Verbal, Quantitative, and Analytical reasoning.",
            content = listOf(
                "Part I — Verbal Reasoning (48% weight): High-frequency academic vocabulary (244 semantic clusters), antonyms, synonyms, analogy relationships, cloze passages, and reading comprehension.",
                "Part II — Quantitative Reasoning (32% weight): Number properties, modular arithmetic, percentages, algebraic equations, coordinate geometry, mensuration, combinatorics, and data sufficiency.",
                "Part III — Analytical & Logical Reasoning (20% weight): Multi-condition logic puzzles, binary truth problems, constraint satisfaction, and deductive arguments."
            ),
            keyPoints = listOf(
                "Verbal dominates overall raw item count.",
                "Quantitative requires quick formula recall and trap recognition.",
                "Analytical requires systematic scratch-paper diagramming."
            )
        ),
        ExamSection(
            number = "VIII",
            title = "Passing Standards for Public vs Private Universities",
            summary = "Institutional variation in cutoff scores and departmental quotas.",
            content = listOf(
                "While the national 50% score qualifies candidates for general postgraduate eligibility, public universities (such as AAU, ASTU, JU, BDU) rank applicants based on combined GAT score, undergraduate CGPA, and departmental entrance examinations.",
                "Private colleges and institutes recognize the standardized NGAT certificate as validation of postgraduate entry credentials."
            ),
            keyPoints = listOf(
                "Public Universities: Higher percentile ranks required for merit-based sponsored slots.",
                "Private Institutions: Standard certificate passing threshold required for enrollment.",
                "Specialized Programs: May weight quantitative or verbal sections preferentially."
            )
        ),
        ExamSection(
            number = "IX",
            title = "Test-Taking Strategy & Time Management Tips",
            summary = "Tactical methodologies to maximize accuracy and pace under tight time limits.",
            content = listOf(
                "1. Pacing Discipline: Keep moving. If a math calculation takes more than 90 seconds, flag it, make an educated elimination guess, and proceed.",
                "2. Elimination Technique: Eliminate extreme wording (always, never, impossible) in Critical Reasoning and decoy near-homophones in Verbal.",
                "3. Scratch Paper Strategy: Create concise symbol tables for analytical seating puzzles rather than holding constraints in memory.",
                "4. Trap Awareness: Beware of common GAT traps such as superficial plausible decoys, false cognates, and inverted causation."
            ),
            keyPoints = listOf(
                "First pass: Answer all high-confidence questions first.",
                "Second pass: Tackle flagged questions with remaining time.",
                "Final 5 minutes: Ensure zero blank answers."
            )
        ),
        ExamSection(
            number = "X",
            title = "Recommended Preparation Roadmap",
            summary = "A structured 4-to-6 week mastery plan using the 80-level curriculum.",
            content = listOf(
                "Phase 1 (Weeks 1-2): Foundation & Diagnostic — Complete Journey Levels 1-31 (Part I Verbal) and master Tier 1 High-Frequency vocabulary clusters.",
                "Phase 2 (Weeks 3-4): Quantitative Core & Traps — Complete Levels 32-61 (Part II Quantitative), study Cheat Sheets, and master all 25 GAT Confused-Word Traps.",
                "Phase 3 (Weeks 5-6): Analytical & Timed Full Simulations — Complete Levels 62-80 (Part III Analytical), run full 180-minute Mock Simulations, and clear Error Notebook."
            ),
            keyPoints = listOf(
                "Daily goal: 20-30 practice questions + 10 SM-2 flashcard reviews.",
                "Weekly full-length timed diagnostic.",
                "Review error explanations immediately after each session."
            )
        ),
        ExamSection(
            number = "XI",
            title = "Exam Day Checklist & Rules",
            summary = "Vital logistics, allowed materials, and testing protocols.",
            content = listOf(
                "Arrive at the testing center at least 45 minutes prior to scheduled start time.",
                "Required: Printed Admission Slip / Ticket, Original Valid ID (Kebele ID, Passport, or Driving License).",
                "Strictly prohibited: Smartphones, smartwatches, external calculators, notebooks, or unauthorized electronic devices.",
                "Scratch paper and pencils will be provided at designated testing workstations."
            ),
            keyPoints = listOf(
                "Arrive 45 minutes early.",
                "Bring valid original identification.",
                "Calculators are not permitted: rely on arithmetic mental shortcuts."
            )
        ),
        ExamSection(
            number = "XII",
            title = "Post-Exam Results & Validity",
            summary = "Result verification, certificate issuance, and validity period.",
            content = listOf(
                "Official test scores are published on the designated university portal within 1 to 2 weeks following test administration.",
                "NGAT / GAT test certificates are officially valid for two (2) academic years from the date of examination, allowing candidates to apply across multiple admission terms."
            ),
            keyPoints = listOf(
                "Results published online within 7-14 days.",
                "Score certificate remains valid for 2 full academic years.",
                "Can be used for admission applications across all recognized graduate schools."
            )
        )
    )
}
