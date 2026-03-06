import { PostData } from '../api';

interface Props {
    post: PostData;
    currentUserId?: string;
    onLikeToggle: (postId: string, liked: boolean) => void;
    onDelete: (postId: string) => void;
}

export default function PostCard({ post, currentUserId, onLikeToggle, onDelete }: Props) {
    const timeAgo = getTimeAgo(post.createdAt);
    const isOwn = currentUserId === post.userId;

    return (
        <div className="post-card">
            <div className="avatar avatar-sm">
                {post.userId.charAt(0).toUpperCase()}
            </div>
            <div className="post-body">
                <div className="post-header">
                    <a
                        href={`/users/${post.userId}`}
                        className="post-username"
                        onClick={e => {
                            e.preventDefault();
                            window.location.href = `/users/${post.userId}`;
                        }}
                    >
                        @{post.userId.slice(0, 8)}
                    </a>
                    <span className="post-time">· {timeAgo}</span>
                </div>
                <div className="post-content">{post.content}</div>
                <div className="post-actions">
                    <button
                        className={`post-action ${post.liked ? 'liked' : ''}`}
                        onClick={() => onLikeToggle(post.id, post.liked)}
                    >
                        <span className="post-action-icon">{post.liked ? '♥' : '♡'}</span>
                        {post.likeCount > 0 && <span>{post.likeCount}</span>}
                    </button>
                    {isOwn && (
                        <button
                            className="post-action"
                            onClick={() => {
                                if (confirm('この投稿を削除しますか？')) {
                                    onDelete(post.id);
                                }
                            }}
                        >
                            <span className="post-action-icon">🗑</span>
                        </button>
                    )}
                </div>
            </div>
        </div>
    );
}

function getTimeAgo(dateStr: string): string {
    const now = new Date();
    const date = new Date(dateStr);
    const seconds = Math.floor((now.getTime() - date.getTime()) / 1000);

    if (seconds < 60) return `${seconds}秒`;
    const minutes = Math.floor(seconds / 60);
    if (minutes < 60) return `${minutes}分`;
    const hours = Math.floor(minutes / 60);
    if (hours < 24) return `${hours}時間`;
    const days = Math.floor(hours / 24);
    if (days < 7) return `${days}日`;
    return date.toLocaleDateString('ja-JP');
}
