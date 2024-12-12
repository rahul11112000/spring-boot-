import React, { useState } from "react";
import { addFund } from "../api/Api";
import { useNavigate } from "react-router-dom";

const AddFund = () => {
  const [amount, setAmount] = useState("");
  const [error, setError] = useState(null);
  const token = localStorage.getItem("token");
  const navigate = useNavigate();
  const handleAddFund = async (e) => {
    e.preventDefault();
    setError(null);

    try {
      await addFund(token, amount);
      navigate("/wallet");
    } catch (err) {
      console.error("Error adding funds:", err);
      setError(err.response?.data?.message || "Failed to add funds.");
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="card shadow p-4" style={{ width: "24rem" }}>
        <h2 className="card-title text-center mb-4">Add Funds</h2>
        <form onSubmit={handleAddFund}>
          <div className="mb-3">
            <label htmlFor="amount" className="form-label">
              Amount
            </label>
            <input
              type="number"
              className="form-control"
              id="amount"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              required
            />
          </div>
          {error && <div className="alert alert-danger">{error}</div>}
          <div className="d-grid gap-2">
            <button type="submit" className="btn btn-primary">
              Add Funds
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default AddFund;
