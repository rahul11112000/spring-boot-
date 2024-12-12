import React, { useState } from "react";
import { transfer } from "../api/Api";
import "bootstrap/dist/css/bootstrap.min.css";

const Transfer = () => {
  const [formData, setFormData] = useState({
    clientemail: "",
    amount: "",
  });
  const [message, setMessage] = useState(null);
  const token = localStorage.getItem("token");
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await transfer(token, formData);
      setMessage(response);
    } catch (error) {
      setMessage("Insert correct email id.");
    }
  };
  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "24rem" }}>
        <h2 className="card-title text-center mb-4">Transfer Money</h2>
        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label htmlFor="email" className="form-label">
              Recipient Email
            </label>
            <input
              type="email"
              className="form-control"
              id="clientemail"
              name="clientemail"
              value={formData.clientemail}
              onChange={handleChange}
              required
            />
          </div>
          <div className="mb-3">
            <label htmlFor="amount" className="form-label">
              Amount
            </label>
            <input
              type="number"
              className="form-control"
              id="amount"
              name="amount"
              value={formData.amount}
              onChange={handleChange}
              required
            />
          </div>
          {message && <div className="alert alert-success">{message}</div>}
          {/* {error && <div className="alert alert-danger">{error}</div>} */}
          <div className="d-grid gap-2">
            <button type="submit" className="btn btn-primary">
              Transfer
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Transfer;
