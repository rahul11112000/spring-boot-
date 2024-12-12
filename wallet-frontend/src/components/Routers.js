import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./Home";
import Login from "./Login";
import WalletPage from "./WalletPage";
import AddFund from "./AddFund";
import Transfer from "./Transfer";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
function Routers() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/wallet" element={<WalletPage />} />
        <Route path="/add/fund" element={<AddFund />} />
        <Route path="/transfer/money" element={<Transfer />} />
      </Routes>
    </BrowserRouter>
  );
}

export default Routers;
