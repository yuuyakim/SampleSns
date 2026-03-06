import { useState, FormEvent } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../api';
import { useAuth } from '../AuthContext';

export default function EditProfilePage() {
    const { user } = useAuth();
    const navigate = useNavigate();
    const [displayName, setDisplayName] = useState(user?.displayName || '');
    const [bio, setBio] = useState(user?.bio || '');
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        if (!user) return;
        setError('');
        setLoading(true);
        try {
            await api.updateProfile(user.id, displayName, bio);
            navigate(`/users/${user.id}`);
        } catch (err) {
            setError(err instanceof Error ? err.message : 'Update failed');
        } finally {
            setLoading(false);
        }
    };

    return (
        <>
            <div className="page-header">
                <h1>プロフィール編集</h1>
            </div>

            <div style={{ padding: '20px' }}>
                {error && <div className="alert alert-error">{error}</div>}

                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label className="form-label">表示名</label>
                        <input
                            id="edit-displayname"
                            type="text"
                            className="form-input"
                            value={displayName}
                            onChange={e => setDisplayName(e.target.value)}
                        />
                    </div>

                    <div className="form-group">
                        <label className="form-label">自己紹介</label>
                        <textarea
                            id="edit-bio"
                            className="form-input"
                            value={bio}
                            onChange={e => setBio(e.target.value)}
                            maxLength={160}
                            rows={4}
                            style={{ resize: 'vertical' }}
                        />
                        <div className="char-count" style={{ textAlign: 'right', marginTop: '4px' }}>
                            {bio.length}/160
                        </div>
                    </div>

                    <div style={{ display: 'flex', gap: '12px' }}>
                        <button
                            type="button"
                            className="btn btn-secondary"
                            onClick={() => navigate(-1)}
                        >
                            キャンセル
                        </button>
                        <button
                            id="edit-submit"
                            type="submit"
                            className="btn btn-primary"
                            disabled={loading}
                        >
                            {loading ? '保存中...' : '保存'}
                        </button>
                    </div>
                </form>
            </div>
        </>
    );
}
