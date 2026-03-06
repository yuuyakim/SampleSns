import { useState, FormEvent } from 'react';
import { api, PostData } from '../api';

interface Props {
    onPostCreated: (post: PostData) => void;
}

export default function PostComposer({ onPostCreated }: Props) {
    const [content, setContent] = useState('');
    const [loading, setLoading] = useState(false);
    const remaining = 140 - content.length;

    const handleSubmit = async (e: FormEvent) => {
        e.preventDefault();
        if (!content.trim() || content.length > 140) return;
        setLoading(true);
        try {
            const post = await api.createPost(content);
            setContent('');
            onPostCreated(post);
        } catch (err) {
            console.error('Post creation failed:', err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <form className="post-composer" onSubmit={handleSubmit}>
            <div style={{ flex: 1 }}>
                <textarea
                    id="post-content"
                    value={content}
                    onChange={e => setContent(e.target.value)}
                    placeholder="いまどうしてる？"
                    maxLength={140}
                />
                <div className="composer-footer">
                    <span className={`char-count ${remaining < 20 ? (remaining < 0 ? 'error' : 'warning') : ''}`}>
                        {remaining}
                    </span>
                    <button
                        id="post-submit"
                        type="submit"
                        className="btn btn-primary btn-sm"
                        disabled={loading || !content.trim() || content.length > 140}
                    >
                        {loading ? '投稿中...' : '投稿する'}
                    </button>
                </div>
            </div>
        </form>
    );
}
