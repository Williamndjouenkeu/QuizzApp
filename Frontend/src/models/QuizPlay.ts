export class QuizPlay {

    quizId: string;
    quizTitle: string;
    playedAt: Date;
    questionsNumber: number;
    correctQuestionsNumber: number;

    constructor(
    quizId: string = "",
    quizTitle: string = "",
    playedAt: Date = new Date(),
    questionsNumber: number = 0,
    correctQuestionsNumber: number = 0,
    ) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.playedAt = playedAt;
        this.questionsNumber = questionsNumber;
        this.correctQuestionsNumber = correctQuestionsNumber;
    }

    static getScoreQuiz(quizPlay: QuizPlay): string {
        return `${Math.ceil(quizPlay.correctQuestionsNumber * 100 / quizPlay.questionsNumber)} %`;
    } 
}