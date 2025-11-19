import { QuizQuestion } from "./QuizQuestion";

export class Quiz {
    id: string;
    title: string;
    description: string;
    tags: string[];
    thumbnail: string;
    questions: QuizQuestion[];

    constructor(
    id: string = "",
    title: string = "",
    description: string = "",
    tags: string[] = [],
    thumbnail: string = "",
    questions: QuizQuestion[] = [new QuizQuestion()]
  ) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.tags = tags;
    this.thumbnail = thumbnail;
    this.questions = questions;
  }
}