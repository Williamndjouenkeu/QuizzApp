export class QuizQuestion {

    question: string;
    options: string[];
    answerIndex: number

    constructor(
    question: string = "",
    options: string[] = [""],
    answerIndex: number = -1
    ) {
        this.question = question;
        this.options = options;
        this.answerIndex = answerIndex;
    }
}