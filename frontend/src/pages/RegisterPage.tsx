import { useState, FormEvent } from 'react';
import { useAuth } from '../AuthContext';
import { Link } from 'react-router-dom';

export default function RegisterPage() {
    const { register } = useAuth();
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        setError('');
        setLoading(true);
        try {
            await register(username, email, password);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Registration failed');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="auth-page">
            <div className="auth-card">
                <h1>SampleSNS</h1>
                <p>アカウントを作成してはじめよう</p>

                {error && <div className="alert alert-error">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className="form-label">ユーザー名</label>
                        <input
                            id="register-username"
                            type="text"
                            className="form-input"
                            placeholder="john_doe"
                            value={username}
                            onChange={e => setUsername(e.target.value)}
                            required
                            minLength={3}
                            maxLength={20}
                            pattern="^[a-zA-Z0-9_]+$"
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">メールアドレス</label>
                        <input
                            id="register-email"
                            type="email"
                            className="form-input"
                            placeholder="you@example.com"
                            value={email}
                            onChange={e => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">パスワード</label>
                        <input
                            id="register-password"
                            type="password"
                            className="form-input"
                            placeholder="8文字以上"
                            value={password}
                            onChange={e => setPassword(e.target.value)}
                            required
                            minLength={8}
                        />
                    </div>

                    <button
                        id="register-submit"
                        type="submit"
                        className="btn btn-primary btn-block"
                        disabled={loading}
                    >
                        {loading ? '登録中...' : 'アカウント作成'}
                    </button>
                </form>

                <div className="auth-footer">
                    すでにアカウントをお持ちの方は <Link to="/login">ログイン</Link>
                </div>
            </div>
        </div>
    );
}
