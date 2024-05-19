import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Button, Form } from "react-bootstrap";
import "./AdminUpdateQuiz.css";
import { useDispatch, useSelector } from "react-redux";
import Sidebar from "../../../components/Sidebar";
import FormContainer from "../../../components/FormContainer";
import * as quizzesConstants from "../../../constants/quizzesConstants";
import { fetchCategories } from "../../../actions/categoriesActions";
import "./AdminUpdateQuiz.css";
import { fetchQuizzes, updateQuiz } from "../../../actions/quizzesActions";
import {notify} from "../../../components/Notify";

const AdminUpdateQuiz = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const params = useParams();
  const quizId = params.quizId;

  const oldQuiz = useSelector((state) =>
    state.quizzesReducer.quizzes.filter((quiz) => quiz.quizId == quizId)
  )[0];

  console.log(oldQuiz);
  const [title, setTitle] = useState(oldQuiz.title);
  const [description, setDescription] = useState(oldQuiz.description);
  const [maxMarks, setMaxMarks] = useState(oldQuiz.maxMarks);
  const [numberOfQuestions, setNumberOfQuestions] = useState(
    oldQuiz.numOfQuestions
  );
  const [isActive, setIsActive] = useState(oldQuiz.isActive);
  const [selectedCategoryId, setSelectedCategoryId] = useState(oldQuiz.category.catId);

  const categoriesReducer = useSelector((state) => state.categoriesReducer);
  const [categories, setCategories] = useState(categoriesReducer.categories);

  const onClickPublishedHandler = () => {
    setIsActive(!isActive);
  };

  const onSelectCategoryHandler = (e) => {
    setSelectedCategoryId(e.target.value);
  };

  const token = JSON.parse(localStorage.getItem("jwtToken"));

  const submitHandler = (e) => {
    e.preventDefault();
    if (selectedCategoryId !== null && selectedCategoryId !== "n/a") {
      const quiz = {
        quizId:quizId,
        title: title,
        description: description,
        isActive: isActive,
        numOfQuestions: numberOfQuestions,
        category: {
          catId: selectedCategoryId,
          title: categories.filter((cat) => cat.catId == selectedCategoryId)[0][
            "title"
          ],
          description: categories.filter(
            (cat) => cat.catId == selectedCategoryId
          )[0]["description"],
        },
      };
      updateQuiz(dispatch, quiz, token).then((data) => {
        if (data.type === quizzesConstants.UPDATE_QUIZ_SUCCESS){
          notify("Quiz Updated!", `${quiz.title} succesfully updated`, "success");
          fetchQuizzes(dispatch, token);
        }
        else {
          notify("Quiz Not Updated!", `${quiz.title} not updated`, "error");
        }
        navigate("/adminQuizzes");
      });
    } else {
      notify("Data Invalid", "Select valid subject!", "warning");
    }
  };

  useEffect(() => {
    if (!localStorage.getItem("jwtToken")) navigate("/");
  }, []);

  useEffect(() => {
    if (categories.length === 0) {
      fetchCategories(dispatch, token).then((data) => {
        setCategories(data.payload);
      });
    }
  }, [categories]);

  return (
    <div className="adminUpdateQuizPage__container">
      <div className="adminUpdateQuizPage__sidebar">
        <Sidebar />
      </div>
      <div className="adminUpdateQuizPage__content">
        <FormContainer>
          <h2 className='profile-heading text-center'>Update Quiz</h2>
          <Form onSubmit={submitHandler} className='custom-form'>
            <Form.Group className="my-3" controlId="title">
              <Form.Label className='poppins-light'>Title</Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter Quiz Title"
                value={title}
                onChange={(e) => {
                  setTitle(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            <Form.Group className="my-3" controlId="description">
              <Form.Label className='poppins-light'>Description</Form.Label>
              <Form.Control
                style={{ textAlign: "top" }}
                as="textarea"
                rows="3"
                type="text"
                placeholder="Enter Quiz Description"
                value={description}
                onChange={(e) => {
                  setDescription(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            {/* <Form.Group className="my-3" controlId="maxMarks">
              <Form.Label>Maximum Marks</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Maximum Marks"
                value={maxMarks}
                onChange={(e) => {
                  setMaxMarks(e.target.value);
                }}
              ></Form.Control>
            </Form.Group>

            <Form.Group className="my-3" controlId="numberOfQuestions">
              <Form.Label>Number of Questions</Form.Label>
              <Form.Control
                type="number"
                placeholder="Enter Number of Questions"
                value={numberOfQuestions}
                onChange={(e) => {
                  setNumberOfQuestions(e.target.value);
                }}
              ></Form.Control>
            </Form.Group> */}

            {/*<Form.Check*/}
            {/*style={{borderColor:"rgb(68 177 49)"}}*/}
            {/*  className="my-3"*/}
            {/*  type="switch"*/}
            {/*  id="publish-switch"*/}
            {/*  label="Publish Quiz"*/}
            {/*  onChange={onClickPublishedHandler}*/}
            {/*  checked={isActive}*/}
            {/*/>*/}

            <div className="my-3">
              <label htmlFor="category-select" className='poppins-light' style={{ paddingBottom:"8px" }}>Choose a Subject:</label>
              <Form.Select
                defaultValue={selectedCategoryId}
                aria-label="Choose Subject"
                id="category-select"
                onChange={onSelectCategoryHandler}
              >
                <option value="n/a">Choose Subject</option>
                {categories ? (
                  categories.map((cat, index) => (
                    <option key={index} value={cat.catId}>
                      {cat.title}
                    </option>
                  ))
                ) : (
                  <option value="">Choose one from below</option>
                )}
                {/* <option value="1">One</option>
                  <option value="2">Two</option>
                  <option value="3">Three</option> */}
              </Form.Select>
            </div>
            <Button
                className="my-3 adminUpdateQuizPage__content--button"
                type="submit"
                variant=""
            >
              Update
            </Button>
          </Form>
        </FormContainer>
      </div>
    </div>
  );
};

export default AdminUpdateQuiz;
