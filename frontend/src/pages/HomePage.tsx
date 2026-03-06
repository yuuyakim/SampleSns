import { useState, useEffect } from 'react';
import { api, PostData } from '../api';
import { useAuth } from '../AuthContext';
import PostCard from '../components/PostCard';
import PostComposer from '../components/PostComposer';

export default function HomePage() {
    const { user } = useAuth();
    const [posts, setPosts] = useState<PostData[]>([]);
    const [tab, setTab] = useState<'home' | 'global'>('home');
    const [loading, setLoading] = useState(true);

    const fetchPosts = async () => {
        setLoading(true);
        try {
            const data = tab === 'home'
                ? await api.getHomeTimeline()
                : await api.getGlobalTimeline();
            setPosts(data);
        } catch {
            setPosts([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPosts();
    }, [tab]);

    const handlePostCreated = (post: PostData) => {
        setPosts(prev => [post, ...prev]);
    };

    const handleLikeToggle = async (postId: string, liked: boolean) => {
        try {
            if (liked) {
                await api.unlike(postId);
            } else {
                await api.like(postId);
            }
            setPosts(prev =>
                prev.map(p =>
                    p.id === postId
                        ? { ...p, liked: !liked, likeCount: liked ? p.likeCount - 1 : p.likeCount + 1 }
                        : p
                )
            );
        } catch (err) {
            console.error('Like toggle failed:', err);
        }
    };

    const handleDelete = async (postId: string) => {
        try {
            await api.deletePost(postId);
            setPosts(prev => prev.filter(p => p.id !== postId));
        } catch (err) {
            console.error('Delete failed:', err);
        }
    };

    return (
        <>
            <div className="page-header">
                <h1>ホーム</h1>
            </div>

            <div className="tabs">
                <button
                    className={`tab ${tab === 'home' ? 'active' : ''}`}
                    onClick={() => setTab('home')}
                >
                    フォロー中
                </button>
                <button
                    className={`tab ${tab === 'global' ? 'active' : ''}`}
                    onClick={() => setTab('global')}
                >
                    みんなの投稿
                </button>
            </div>

            <PostComposer onPostCreated={handlePostCreated} />

            {loading ? (
                <div className="loading"><div className="spinner" /></div>
            ) : posts.length === 0 ? (
                <div className="empty-state">
                    <div className="empty-state-icon">📭</div>
                    <h3>投稿がありません</h3>
                    <p>{tab === 'home' ? 'フォローしているユーザーの投稿が表示されます' : 'まだ誰も投稿していません'}</p>
                </div>
            ) : (
                posts.map(post => (
                    <PostCard
                        key={post.id}
                        post={post}
                        currentUserId={user?.id}
                        onLikeToggle={handleLikeToggle}
                        onDelete={handleDelete}
                    />
                ))
            )}
        </>
    );
}
