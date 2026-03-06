import { BrowserRouter, Routes, Route, Navigate, NavLink, useNavigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './AuthContext';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import HomePage from './pages/HomePage';
import ProfilePage from './pages/ProfilePage';
import EditProfilePage from './pages/EditProfilePage';
import './index.css';

function AppLayout() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  if (!user) return <Navigate to="/login" />;

  const initial = (user.displayName || user.username).charAt(0).toUpperCase();

  return (
    <div className="app-layout">
      <aside className="sidebar">
        <div className="sidebar-logo">SampleSNS</div>
        <nav className="sidebar-nav">
          <NavLink to="/" className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`} end>
            <span className="icon">🏠</span>
            <span>ホーム</span>
          </NavLink>
          <NavLink
            to={`/users/${user.id}`}
            className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
          >
            <span className="icon">👤</span>
            <span>プロフィール</span>
          </NavLink>
          <NavLink
            to="/settings/profile"
            className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`}
          >
            <span className="icon">⚙️</span>
            <span>設定</span>
          </NavLink>
        </nav>
        <div
          className="sidebar-user"
          onClick={() => navigate(`/users/${user.id}`)}
          role="button"
          tabIndex={0}
        >
          <div className="avatar avatar-sm">{initial}</div>
          <div style={{ flex: 1, minWidth: 0 }}>
            <div style={{ fontWeight: 700, fontSize: '14px', whiteSpace: 'nowrap', overflow: 'hidden', textOverflow: 'ellipsis' }}>
              {user.displayName || user.username}
            </div>
            <div style={{ fontSize: '13px', color: 'var(--text-muted)' }}>@{user.username}</div>
          </div>
          <button
            className="btn btn-secondary btn-sm"
            onClick={e => {
              e.stopPropagation();
              logout();
            }}
            style={{ padding: '4px 12px', fontSize: '12px' }}
          >
            ログアウト
          </button>
        </div>
      </aside>

      <main className="main-content">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/users/:userId" element={<ProfilePage />} />
          <Route path="/settings/profile" element={<EditProfilePage />} />
        </Routes>
      </main>
    </div>
  );
}

function AppRoutes() {
  const { user, loading } = useAuth();

  if (loading) {
    return (
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '100vh' }}>
        <div className="spinner" />
      </div>
    );
  }

  return (
    <Routes>
      <Route path="/login" element={user ? <Navigate to="/" /> : <LoginPage />} />
      <Route path="/register" element={user ? <Navigate to="/" /> : <RegisterPage />} />
      <Route path="/*" element={<AppLayout />} />
    </Routes>
  );
}

export default function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </BrowserRouter>
  );
}
