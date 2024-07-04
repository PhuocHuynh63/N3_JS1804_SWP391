import React, { useState } from "react";
import { userService } from "../../service/userService";
import { Modal, Button } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./LoginPage.css";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { localService } from "../../service/localService";
import { setLoginAction } from "../../redux/action/UserAction";
import { Input, Form, notification } from "antd";
import bannerLogin from "../../images/Logo_Login.jpg";

const LoginPage = ({ show, handleClose }) => {
  const [loading, setLoading] = useState(false);
  const [password, setPassword] = useState('');
  const [showError, setShowError] = useState(false);
  let dispatch = useDispatch();
  let navigate = useNavigate();

  const handlePasswordChange = (e) => {
    const value = e.target.value;
    setPassword(value);
    setShowError(value.length > 0 && value.length < 6);
  };

  const onFinish = (values) => {
    setLoading(true); // Bắt đầu tải
    console.log("Success:", values);
    userService
      .postLogin(values)
      .then((response) => {

        if (response.success) {
          localService.set(response.data); // Lưu token vào local storage
          dispatch(setLoginAction({ token: response.data, role: response.role })); // Lưu token và vai trò vào redux

          notification.success({
            message: "Đăng nhập thành công",
            description: response.description,
          });

          if (response.role === "admin") {
            navigate("/adminPage");
          } else {
            navigate("/");
          }

          setTimeout(() => {
            window.location.reload();
          }, 1000); // Reload the page after 1 second
        } else {
          throw new Error(response.description);
        }
      })
      .catch((error) => {
        console.log("API lỗi:", error);
        notification.error({
          message: "Đăng nhập thất bại",
          description: error.message,
        });
      })
      .finally(() => {
        setLoading(false); // Hoàn thành tải
      });
  };

  const onFinishFailed = (errorInfo) => {
    console.log(`Failed:`, errorInfo);
    notification.error({
      message: "Đăng nhập thất bại",
      description: "Kiểm tra lại tên đăng nhập và mật khẩu",
    });
  };

  const handleRegister = () => {
    handleClose();
    navigate('/signup');
  };

  const handleForgot = () => {
    handleClose();
    navigate('/reset-password');
  };

  return (
    <Modal show={show} onHide={handleClose} size="xl" centered>
      <div className="login-modal-content">
        <section className="bg-light p-3 p-md-4 p-xl-5">
          <div className="container">
            <div className="row justify-content-center">
              <div className="col-12 col-xxl-11">
                <div className="card border-light-subtle shadow-sm">
                  <div className="row g-0">
                    <div className="col-12 col-md-6">
                      <img
                        className="img-fluid rounded-start w-100 h-100 object-fit-cover"
                        loading="lazy"
                        src={bannerLogin}
                        alt="Welcome back you've been missed!"
                      />
                    </div>
                    <div className="col-12 col-md-6 d-flex align-items-center justify-content-center">
                      <Button
                        variant="close"
                        aria-label="Close"
                        onClick={handleClose}
                        className="btn-close-modal"
                      ></Button>
                      <div className="col-12 col-lg-11 col-xl-10">
                        <div className="card-body p-3 p-md-4 p-xl-5">
                          <div className="row">
                            <div className="col-12">
                              <div className="mb-5">
                                <h4 className="text-center">
                                  Chào mừng bạn đã trở lại!
                                </h4>
                              </div>
                            </div>
                          </div>
                          <div className="row">
                            <div className="col-12">
                              <div className="d-flex gap-3 flex-column">
                                <a
                                  href="#!"
                                  className="btn btn-lg btn-outline-dark"
                                >
                                  <svg
                                    xmlns="http://www.w3.org/2000/svg"
                                    width="16"
                                    height="16"
                                    fill="currentColor"
                                    className="bi bi-google"
                                    viewBox="0 0 16 16"
                                  >
                                    <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z" />
                                  </svg>
                                  <span className="ms-2 fs-6">
                                    Đăng nhập với Google
                                  </span>
                                </a>
                              </div>
                              <p className="text-center mt-4 mb-5">
                                Hoặc đăng nhập bằng
                              </p>
                            </div>
                          </div>
                          <Form
                            name="basic"
                            initialValues={{ remember: true }}
                            onFinish={onFinish}
                            onFinishFailed={onFinishFailed}
                            autoComplete="off"
                            layout="vertical"
                          >
                            <div className="row gy-3 overflow-hidden">
                              <div className="col-12">
                                <div className="form-floating mb-3">
                                  <Form.Item
                                    name="username"
                                    rules={[
                                      {
                                        required: true,
                                        message: "Please input your username!",
                                      },
                                    ]}
                                    noStyle
                                  >
                                    <Input
                                      id="username"
                                      type="text"
                                      className="form-control"
                                      placeholder="Tên đăng nhập"
                                      required
                                    />
                                  </Form.Item>
                                  <label
                                    htmlFor="username"
                                    className="form-label"
                                  >
                                    Tên đăng nhập
                                  </label>
                                </div>
                              </div>
                              <div className="col-12">
                                <div className="form-floating mb-3">
                                  <Form.Item
                                    name="password"
                                    rules={[
                                      {
                                        required: true,
                                        message: "Please input your password!",
                                      },
                                      {
                                        min: 6,
                                        message: "Mật khẩu tối thiểu 6 ký tự",
                                      },
                                    ]}
                                    noStyle
                                  >
                                    <Input
                                      id="password"
                                      type="password"
                                      className="form-control"
                                      placeholder="Mật khẩu"
                                      required
                                      value={password}
                                      onChange={handlePasswordChange}
                                    />
                                  </Form.Item>
                                  <label htmlFor="password" className="form-label">
                                    Mật khẩu
                                  </label>
                                  {showError && (
                                    <span className="text-danger">
                                      Mật khẩu tối thiểu 6 ký tự
                                    </span>
                                  )}
                                </div>
                              </div>
                              <div className="col-12">
                                <div className="d-grid">
                                  <Form.Item noStyle>
                                    <Button
                                      id="btn-signin"
                                      className="btn btn-dark btn-lg"
                                      type="submit"
                                      disabled={loading} // Vô hiệu hóa nút khi đang tải
                                    >
                                      {loading ? "Đang xử lý..." : "Đăng nhập"}
                                    </Button>
                                  </Form.Item>
                                </div>
                              </div>
                            </div>
                          </Form>
                          <div className="row">
                            <div className="col-12">
                              <div className="d-flex gap-2 gap-md-4 flex-column flex-md-row justify-content-md-center mt-5">
                                <span
                                  className="link-secondary text-decoration-none"
                                  onClick={handleRegister}
                                >
                                  Tạo tài khoản
                                </span>
                                <span
                                  className="link-secondary text-decoration-none"
                                  onClick={handleForgot}
                                >
                                  Quên mật khẩu
                                </span>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </Modal>
  );
};

export default LoginPage;
