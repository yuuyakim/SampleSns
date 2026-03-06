import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { api, UserData, PostData, FollowData } from '../api';
import { useAuth } from '../AuthContext';
import PostCard from '../components/PostCard';

export default function ProfilePage() {
    const { userId } = useParams<{ userId: string }>();
    const { user: currentUser } = useAuth();
    const navigate = useNavigate();

    const [profileUser, setProfileUser] = useState<UserData | null>(null);
    const [posts, setPosts] = useState<PostData[]>([]);
    const [followers, setFollowers] = useState<FollowData[]>([]);
    const [following, setFollowing] = useState<FollowData[]>([]);
    const [isFollowing, setIsFollowing] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (!userId) return;
        setLoading(true);

        Promise.all([
            api.getUser(userId),
            api.getUserPosts(userId),
            api.getFollowers(userId),
            api.getFollowing(userId),
        ])
            .then(([userData, postsData, followersData, followingData]) => {
                setProfileUser(userData);
                setPosts(postsData);
                setFollowers(followersData);
                setFollowing(followingData);
                if (currentUser) {
                    setIsFollowing(followersData.some(f => f.userId === currentUser.id));
                }
            })
            .catch(() => navigate('/'))
            .finally(() => setLoading(false));
    }, [userId, currentUser]);

    const handleFollow = async () => {
        if (!userId) return;
        try {
            if (isFollowing) {
                await api.unfollow(userId);
                setIsFollowing(false);
                setFollowers(prev => prev.filter(f => f.userId !== currentUser?.id));
            } else {
                await api.follow(userId);
                setIsFollowing(true);
                setFollowers(prev => [...prev, { userId: currentUser!.id, createdAt: new Date().toISOString() }]);
            }
        } catch (err) {
            console.error('Follow toggle failed:', err);
        }
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

    if (loading) {
        return (
            <>
                <div className="page-header"><h1>プロフィール</h1></div>
                <div className="loading"><div className="spinner" /></div>
            </>
        );
    }

    if (!profileUser) return null;

    const isOwn = currentUser?.id === profileUser.id;
    const initial = (profileUser.displayName || profileUser.username).charAt(0).toUpperCase();

    return (
        <>
            <div className="page-header">
                <h1>{profileUser.displayName || profileUser.username}</h1>
            </div>

            <div className="profile-header">
                <div className="profile-banner" />
                <div className="profile-info">
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start' }}>
                        <div className="avatar avatar-lg" style={{ marginTop: '-20px' }}>{initial}</div>
                        <div className="profile-actions">
                            {isOwn ? (
                                <button
                                    className="btn btn-secondary btn-sm"
                                    onClick={() => navigate('/settings/profile')}
                                >
                                    プロフィールを編集
                                </button>
                            ) : (
                                <button
                                    className={`btn ${isFollowing ? 'btn-secondary' : 'btn-primary'} btn-sm`}
                                    onClick={handleFollow}
                                >
                                    {isFollowing ? 'フォロー中' : 'フォローする'}
                                </button>
                            )}
                        </div>
                    </div>
                    <div style={{ marginTop: '12px' }}>
                        <div className="profile-name">{profileUser.displayName || profileUser.username}</div>
                        <div className="profile-handle">@{profileUser.username}</div>
                        {profileUser.bio && <div className="profile-bio">{profileUser.bio}</div>}
                        <div className="profile-stats">
                            <div>
                                <span className="stat-value">{following.length}</span>{' '}
                                <span className="stat-label">フォロー中</span>
                            </div>
                            <div>
                                <span className="stat-value">{followers.length}</span>{' '}
                                <span className="stat-label">フォロワー</span>
                            </div>
                            <div>
                                <span className="stat-value">{posts.length}</span>{' '}
                                <span className="stat-label">投稿</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            {posts.length === 0 ? (
                <div className="empty-state">
                    <div className="empty-state-icon">📝</div>
                    <h3>まだ投稿がありません</h3>
                </div>
            ) : (
                posts.map(post => (
                    <PostCard
                        key={post.id}
                        post={post}
                        currentUserId={currentUser?.id}
                        onLikeToggle={handleLikeToggle}
                        onDelete={handleDelete}
                    />
                ))
            )}
        </>
    );
}
