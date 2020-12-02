import React, { Component } from "react";
import AppNav from "./AppNav";
import DatePicker from "react-datepicker";
import "./App.css";
import "react-datepicker/dist/react-datepicker.css";
import {
  Table,
  Container,
  Input,
  Button,
  Label,
  Form,
  FormGroup,
} from "reactstrap";
import { Link } from "react-router-dom";
import Moment from "react-moment";

class Expenses extends Component {
  // {
  //     "id": 100,
  //     "expensedate": "2019-06-16T17:00:00Z",
  //     "description": "New York Business Trip",
  //     "location": "New York",
  //     "category": {
  //     "id": 1,
  //     "name": "Travel"
  //     }
  //     },
  emptyItem = {
    description: "",
    expensedate: new Date(),
    id: "104",
    location: "",
    category: { id: 1, name: "Travel" },
  };
  constructor(props) {
    super(props);

    this.state = {
      date: new Date(),
      isLoading: false,
      Categories: [],
      Expenses: [],
      item: this.emptyItem,
    };
    this.handleSubmit = this.handleSubmit.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleDateChange = this.handleDateChange.bind(this);
  }

  async handleSubmit(event) {
    const item = this.state.item;
    await fetch("/api/expenses", {
      method: "POST",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(item),
    });
    console.log(this.state);
    event.preventDefault();
    this.props.history.push("/expenses");
  }

  handleChange(event) {
    const target = event.target;
    const value = target.value;
    const name = target.name;
    let item = { ...this.state.item };
    item[name] = value;
    this.setState({ item });
    // console.log(this.state.item);
    console.log(item);
  }
  handleDateChange(date) {
    let item = { ...this.state.item };
    item.expensedate = date;
    this.setState({ item });
  }

  async remove(id) {
    await fetch(`/api/expenses/${id}`, {
      method: "DELETE",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    }).then(() => {
      let updateExpenses = [...this.state.Expenses].filter((i) => i.id !== id);
      this.setState({ Expenses: updateExpenses });
    });
  }

  async componentDidMount() {
    const response = await fetch("/api/categories");
    const body = await response.json();
    this.setState({ Categories: body, isLoading: false });

    const responseExp = await fetch("/api/expenses");
    const bodyExp = await responseExp.json();
    this.setState({ Expenses: bodyExp, isLoading: false });
  }

  render() {
    const title = <h3>Add Expense</h3>;
    const { Categories } = this.state;
    const { Expenses, isLoading } = this.state;
    if (isLoading) {
      return <div>Loading...</div>;
    } else {
      let optionList = Categories.map((category) => {
        return <option id={category.id}>{category.name}</option>;
      });
      let rows = Expenses.map((expense) => {
        return (
          <tr key={expense.id}>
            <td>{expense.description}</td>
            <td>{expense.location}</td>
            <td>
              <Moment date={expense.expensedate} format="YYYY/MM/DD" />
            </td>
            <td>{expense.category.name}</td>
            <td>
              <Button
                size="sm"
                color="danger"
                onClick={() => this.remove(expense.id)}
              >
                Delete
              </Button>
            </td>
          </tr>
        );
      });
      return (
        <div>
          <AppNav />
          <Container>
            {title}
            <Form onSubmit={this.handleSubmit}>
              <FormGroup>
                <Label for="title">Title</Label>
                <Input
                  type="text"
                  name="title"
                  id="title"
                  onChange={this.handleChange}
                  autoComplete="name"
                />
              </FormGroup>
              <FormGroup>
                <Label for="category">Category</Label>
                <select>{optionList}</select>
              </FormGroup>
              <FormGroup>
                <Label for="city">Date</Label>
                <DatePicker
                  selected={this.state.item.expensedate}
                  onChange={this.handleDateChange}
                />
              </FormGroup>
              <div className="row">
                <FormGroup className="col-md-4 mb-3">
                  <Label for="location">Location</Label>
                  <Input type="text" name="location" id="location" />
                </FormGroup>
              </div>
              <FormGroup>
                <Button color="primary" type="submit">
                  Save
                </Button>{" "}
                <Button color="secondary" tag={Link} to="/categories">
                  Cancel
                </Button>
              </FormGroup>
            </Form>
          </Container>{" "}
          <Container>
            <h3>Expense List</h3>
            <Table className="mt-4">
              <thead>
                <tr>
                  <th width="30%">Description</th>
                  <th width="10%">Location</th>
                  <th>Date</th>
                  <th>Category</th>
                  <th width="10%">Action</th>
                </tr>
              </thead>
              <tbody>{rows}</tbody>
            </Table>
          </Container>
        </div>
      );
    }
  }
}
export default Expenses;
